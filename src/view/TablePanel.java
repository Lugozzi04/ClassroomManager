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
import java.awt.print.PrinterException;
/**
 *  TablePanel è una classe che implementa un JPanel per la tabella
 */
public class TablePanel extends JPanel implements ActionListener {
    private JDatePickerImpl datePicker;
    private JTable table;
    private List<Classroom> classrooms;
    private ModelManager modelManager;
    private UtilDateModel model;
    private TableMouseListener listenerMouse;

    private static final int NUMBER_OF_ROWS = 10;
    private static final int ROW_HEIGHT = 30;
    /**
     * Costruttore della classe TablePanel
     * @param modelManager
     */
    public TablePanel(ModelManager modelManager) {
        this.modelManager = modelManager;
        this.classrooms = modelManager.getClassrooms();
        
        setLayout(new BorderLayout());
        
        initializeComponents();
        updateTable();
    }
    /**
     * Inizializza i componenti del pannello
     */
    private void initializeComponents() {
        configureDatePicker();
        configureTable();
        configureButtons();
    }
    /**
     * Configura il date picker del pannello
     */
    private void configureDatePicker() {
        model = new UtilDateModel();
        model.setSelected(true);

        Properties datePickerProperties = new Properties();
        datePickerProperties.put("text.today", "Today");
        datePickerProperties.put("text.month", "Month");
        datePickerProperties.put("text.year", "Year");
        
        JDatePanelImpl datePanel = new JDatePanelImpl(model, datePickerProperties);
        datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        
        JButton datePickerButton = (JButton) datePicker.getComponent(1);
        datePickerButton.setText("Scegli Data");
        datePickerButton.revalidate();
        datePickerButton.repaint();

        datePicker.addActionListener(this);
        add(datePicker, BorderLayout.NORTH);
    }
    /**
     * Configura la tabella del pannello
     */
    private void configureTable() {
        table = new JTable();
        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
        table.setRowHeight(ROW_HEIGHT);

        listenerMouse = new TableMouseListener(this, table, modelManager, model);
        table.addMouseListener(listenerMouse);
        
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
    /**
     * Configura i bottoni del pannello
     */
    private void configureButtons() {
        JButton printButton = new JButton("Stampa");
        printButton.addActionListener(this);
        add(printButton, BorderLayout.SOUTH);
    }
    /**
     * Aggiorna la tabella
     */
    public void updateTable() {
        LocalDate selectedDate = getSelectedDate();
        String[] columnNames = createColumnNames();
        String[][] data = createTableData(selectedDate);
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rende tutte le celle non modificabili
            }
        };
        table.setModel(tableModel);
    }
    /**
     * Metodo che restituisce la data selezionata
     * @return
     */
    private LocalDate getSelectedDate() {
        if (model == null) {
            return LocalDate.now();
        }
        return LocalDate.of(model.getYear(), model.getMonth() + 1, model.getDay());
    }
    /**
     * Metodo che crea i nomi delle colonne
     * @return
     */
    private String[] createColumnNames() {
        String[] columnNames = new String[classrooms.size() + 1];
        columnNames[0] = "Orario";
        for (int i = 0; i < classrooms.size(); i++) {
            columnNames[i + 1] = "Aula " + classrooms.get(i).getNumber();
        }
        return columnNames;
    }
    /**
     * Metodo che crea i dati della tabella
     * @param date
     * @return
     */
    private String[][] createTableData(LocalDate date) {
        String[][] data = new String[NUMBER_OF_ROWS][classrooms.size() + 1];
        String[] hours = {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};

        for (int row = 0; row < NUMBER_OF_ROWS; row++) {
            data[row][0] = hours[row] + " - " + hours[row + 1];
            for (int col = 0; col < classrooms.size(); col++) {
                Reservation res = modelManager.getReservation(date, classrooms.get(col).getNumber(), row + 8);
                data[row][col + 1] = (res != null) ? res.getName() : "";
            }
        }
        return data;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String actionCommand = ae.getActionCommand();
        if (actionCommand.equals("Date selected")) {
            updateTable();
        } else if (actionCommand.equals("Stampa")) {
            printTable();
        }
    }
    /**
     * Metodo che stampa la tabella
     */
    private void printTable() {
        try {
            boolean complete = table.print();
            String message = complete ? "Stampa completata!" : "Stampa annullata!";
            JOptionPane.showMessageDialog(null, message, complete ? "Successo" : "Attenzione", complete ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(null, "Errore di stampa: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Classe che implementa un render personalizzato per le celle della tabella
     */
    private static class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 0) {
                cell.setBackground(Color.YELLOW);
            } else if ("".equals(value)) {
                cell.setBackground(Color.WHITE);
            } else {
                cell.setBackground(Color.RED);
            }
            return cell;
        }
    }

}
