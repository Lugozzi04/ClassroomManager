package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import org.jdatepicker.impl.*;
import model.entities.Classroom;
import model.entities.Reservation;
import control.ModelManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;


public class TablePanel extends JPanel implements ActionListener {
    private JDatePickerImpl datePicker;
    private JTable table;
    private List<Classroom> classrooms;
    private ModelManager modelManager;
    private UtilDateModel model;
    private TableMouseListener listenerMouse;




    public TablePanel(ModelManager modelManager) {
        this.modelManager = modelManager;
        this.classrooms = modelManager.getClassrooms();


        setLayout(new BorderLayout());

        configureDatePicker();
        configureTable();

        add(datePicker, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        updateTable();
    }

    private void configureDatePicker() {
        model = new UtilDateModel();
        model.setSelected(true);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, null);

        JButton datePickerButton = (JButton) datePicker.getComponent(1);
        datePickerButton.setText("Scegli Data");
        datePickerButton.revalidate();
        datePickerButton.repaint();
        JButton printButton = new JButton("Stampa");
        printButton.addActionListener(this);
        add(printButton, BorderLayout.SOUTH);
        datePicker.addActionListener(this);
    }

    private void configureTable() {
        table = new JTable();
        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
        table.setRowHeight(30); 

        listenerMouse = new TableMouseListener(this,table, modelManager, model);
        table.addMouseListener(listenerMouse);  // Registriamo il listener alla tabella

    }

    public void updateTable() {
        LocalDate date;
        if(model == null) 
            date=LocalDate.now();
        else
            date= LocalDate.of(model.getYear(), model.getMonth() + 1, model.getDay());
        String[] columnNames = new String[classrooms.size() + 1];
        columnNames[0] = "Orario";
        for (int i = 0; i < classrooms.size(); i++) {
            columnNames[i + 1] = "Aula " + classrooms.get(i).getNumber();
        }

        String[][] data = new String[10][classrooms.size() + 1];
        String[] hours = {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00","18:00"};

        for (int row = 0; row < 10; row++) {
            data[row][0] = hours[row] + " - " + hours[row + 1];
            for (int col = 0; col < classrooms.size(); col++) {
                Reservation res = modelManager.getReservation(date, classrooms.get(col).getNumber(), row + 8);
                if(res != null){
                    data[row][col + 1]= "Prenotato";
                }else{
                    data[row][col + 1] = "";
                }
            }
        }
        table.setModel(new DefaultTableModel(data, columnNames));
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println("Data selezionata: " + model.getValue());
        System.out.println(ae.getActionCommand());
        if(ae.getActionCommand().equals("Date selected")) 
            updateTable();
        if(ae.getActionCommand().equals("Stampa")) 
            printTable();
        
    }

    private void printTable() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Print Table");

        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics pg, PageFormat pf, int pageNum) {
                if (pageNum > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2 = (Graphics2D) pg;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                g2.scale(0.75, 0.75); // Adjust the scale as needed

                table.print(g2);

                return Printable.PAGE_EXISTS;
            }
        });

        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Errore durante la stampa: " + e.getMessage(), "Errore di Stampa", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nessuna stampante disponibile.", "Errore di Stampa", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if ("Prenotato".equals(value)) {
                cell.setBackground(Color.RED);
            } else {
                cell.setBackground(Color.WHITE);
            }
            return cell;
        }
    }
}
