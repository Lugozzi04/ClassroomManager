package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import javax.swing.*;

import org.jdatepicker.impl.*;

import model.entities.Classroom;
import model.entities.Reservation;
/**
 * Classe che rappresenta un dialog per l'inserimento e la modifica di una prenotazione.
 */
public final class ReservationDialog extends JDialog {
    
    private final JComboBox<Classroom> classroomsComboBox;
    private final JComboBox<String> startHourComboBox;
    private final JComboBox<String> endHourComboBox;
    private final JTextField nameField;
    private final JTextField descriptionField;
    private final JButton submitButton;

    private  UtilDateModel model;
    private JDatePickerImpl datePicker;

    private final boolean isEditable;
    /**
     * Costruttore della classe.
     * @param classrooms
     */
    public ReservationDialog(List<Classroom> classrooms) {
        super();
        this.classroomsComboBox = new JComboBox<>();
        classrooms.forEach(classroomsComboBox::addItem);

        this.startHourComboBox = createHourComboBox();
        this.endHourComboBox = createHourComboBox();

        this.submitButton = new JButton("Aggiungi Prenotazione");
        this.datePicker = createDatePicker();
        this.nameField = new JTextField();
        this.descriptionField = new JTextField();
        this.isEditable = true;
        initComponents();
    }
    /**
     * Costruttore della classe.
     * @param classroom
     * @param startHour
     * @param endHour
     * @param date
     */
    public ReservationDialog(Classroom classroom, int startHour, int endHour,LocalDate date) {
        super();
        this.classroomsComboBox = new JComboBox<>(new Classroom[]{classroom});
        this.startHourComboBox = createHourComboBox();//new JComboBox<>(new String[]{formatHour(startHour)});
        this.endHourComboBox = createHourComboBox();//new JComboBox<>(new String[]{formatHour(endHour)});

        this.submitButton = new JButton("Modifica Prenotazione");
        this.datePicker = createDatePicker();
        this.model = (UtilDateModel) datePicker.getModel();
        model.setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        model.setSelected(true);

        this.nameField = new JTextField();
        this.descriptionField = new JTextField();
        this.isEditable = false;

        startHourComboBox.setSelectedItem(formatHour(startHour));
        endHourComboBox.setSelectedItem(formatHour(endHour));
        
        initComponents();
    }
    /**
     * Metodo che inizializza i componenti del dialog.
     */
    private void initComponents() {
        setLayout(new GridLayout(7, 2));

        classroomsComboBox.setEnabled(isEditable);
        //startHourComboBox.setEnabled(isEditable);
        //endHourComboBox.setEnabled(isEditable);
        datePicker.setEnabled(isEditable);

        add(new JLabel("Aula:"));
        add(classroomsComboBox);
        if(isEditable){
            add(new JLabel("Data:"));
            add(datePicker);
        }
        add(new JLabel("Ora Inizio:"));
        add(startHourComboBox);
        add(new JLabel("Ora Fine:"));
        add(endHourComboBox);
        add(new JLabel("Nome:"));
        add(nameField);
        add(new JLabel("Descrizione:"));
        add(descriptionField);
        add(new JLabel());
        add(submitButton);

        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Metodo che crea un JComboBox di ore.
     * @return
     */
    private JComboBox<String> createHourComboBox() {
        String[] ore = {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};
        return new JComboBox<>(ore);
    }
    /**
     * Metodo che formatta l'ora in modo da avere sempre due cifre.
     * @param hour
     * @return
     */
    private String formatHour(int hour) {
        return String.format("%02d:00", hour);
    }
    /**
     * Metodo che crea un JDatePicker per la selezione della data.
     * @return
     */
    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        LocalDate now = LocalDate.now();
        model.setDate(now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth());
        model.setSelected(true);

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateComponentFormatter());
    }
    /**
     * Metodo che aggiunge un ActionListener al pulsante di submit.
     * @param listener
     */
    public void addActionListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }
    /**
     * Metodo che restituisce il comando del pulsante di submit.
     * @return
     */
    public String getSubmitButtonCommand() {
        return submitButton.getActionCommand();
    }
    /**
     * Metodo che restituisce la prenotazione selezionata.
     * @return
     */
    public Reservation getSelectedReservation() {
        LocalDate date;
        if(isEditable){
            date = LocalDate.of(
            datePicker.getModel().getYear(),
            datePicker.getModel().getMonth() + 1,
            datePicker.getModel().getDay()
        );
        }else{
            date = LocalDate.of(
            model.getYear(),
            model.getMonth() + 1,
            model.getDay()
        );
        }
        int startHour = Integer.parseInt(((String) startHourComboBox.getSelectedItem()).split(":")[0]);
        int endHour = Integer.parseInt(((String) endHourComboBox.getSelectedItem()).split(":")[0]);
        if(nameField.getText().isEmpty() || descriptionField.getText().isEmpty()|| startHour >= endHour){
            JOptionPane.showMessageDialog(null, "I dati inseriti non sono validi", "Errore", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return new Reservation(date, startHour, endHour, nameField.getText(), descriptionField.getText());
        
    }
    /**
     * Metodo che restituisce il numero dell'aula selezionata.
     * @return
     */
    public int getSelectedClassNumber() {
        return ((Classroom) classroomsComboBox.getSelectedItem()).getNumber();
    }
}
