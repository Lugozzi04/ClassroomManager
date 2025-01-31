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

    // Costruttore
    public TablePanel(ModelManager modelManager) {
        this.modelManager = modelManager;
        this.classrooms = modelManager.getClassrooms();
        
        setLayout(new BorderLayout());
        configureDatePicker();
        configureTable();
        initializeUI();
    }

    // Configura il DatePicker
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
    }

    // Configura la JTable
    private void configureTable() {
        table = new JTable();
        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
        table.setRowHeight(30);
        listenerMouse = new TableMouseListener(this, table, modelManager, model);
        table.addMouseListener(listenerMouse);  // Aggiungi il listener alla tabella
    }

    // Inizializza l'interfaccia utente aggiungendo il DatePicker e la Tabella
    private void initializeUI() {
        JButton printButton = new JButton("Stampa");
        printButton.addActionListener(this);
        
        // Aggiungi i componenti
        add(datePicker, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(printButton, BorderLayout.SOUTH);
        
        // Carica inizialmente la tabella
        updateTable();
    }

    // Aggiorna la tabella in base alla data selezionata
    public void updateTable() {
        LocalDate date = getSelectedDate();
        String[] columnNames = createColumnNames();
        String[][] data = createTableData(date);

        table.setModel(new DefaultTableModel(data, columnNames));
    }

    // Ottieni la data selezionata
    private LocalDate getSelectedDate() {
        if (model == null) return LocalDate.now();
        return LocalDate.of(model.getYear(), model.getMonth() + 1, model.getDay());
    }

    // Crea i nomi delle colonne della tabella
    private String[] createColumnNames() {
        String[] columnNames = new String[classrooms.size() + 1];
        columnNames[0] = "Orario";
        for (int i = 0; i < classrooms.size(); i++) {
            columnNames[i + 1] = "Aula " + classrooms.get(i).getNumber();
        }
        return columnNames;
    }

    // Crea i dati per la tabella in base alla data
    private String[][] createTableData(LocalDate date) {
        String[][] data = new String[10][classrooms.size() + 1];
        String[] hours = {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00","18:00"};

        for (int row = 0; row < 10; row++) {
            data[row][0] = hours[row] + " - " + hours[row + 1];
            for (int col = 0; col < classrooms.size(); col++) {
                Reservation res = modelManager.getReservation(date, classrooms.get(col).getNumber(), row + 8);
                data[row][col + 1] = (res != null) ? res.getName() : "";
            }
        }
        return data;
    }

    // Gestisce gli eventi come la selezione della data e la stampa della tabella
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Date selected")) {
            updateTable();  // Quando la data cambia, aggiorna la tabella
        } else if (ae.getActionCommand().equals("Stampa")) {
            printTable();   // Quando si preme Stampa, avvia la stampa della tabella
        }
    }

    // Gestisce la stampa della tabella
    private void printTable() {
        try {
            boolean complete = table.print();
            if (complete) {
                JOptionPane.showMessageDialog(null, "Stampa completata!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Stampa annullata!", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(null, "Errore di stampa: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Renderizza le celle della tabella con colori personalizzati
    private static class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            // Personalizzazione del colore della cella
            if (column == 0) {
                cell.setBackground(Color.YELLOW);  // Colore per la colonna "Orario"
            } else if ("".equals(value)) {
                cell.setBackground(Color.WHITE);  // Celle vuote
            } else {
                cell.setBackground(Color.RED);    // Celle con prenotazione
            }
            
            return cell;
        }
    }
}
