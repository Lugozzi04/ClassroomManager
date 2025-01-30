package view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import model.entities.Reservation;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import model.entities.Classroom;

public final class ReservationDialog extends JDialog {
    
    private final JComboBox<Classroom> classroomsComboBox;
    private final JComboBox<String> startHourComboBox;
    private final JComboBox<String> endHourComboBox;

    private JDatePickerImpl datePicker;
    private JTextField nameField;
    private JTextField descriptionField;
    private JButton submitButton;

    public ReservationDialog(List<Classroom> classrooms) {
        super();
        classroomsComboBox = new JComboBox<>();
        for(Classroom classroom : classrooms) {
            this.classroomsComboBox.addItem(classroom);
        }

        String[] ore = {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};
        startHourComboBox= new JComboBox<>(ore);
        endHourComboBox = new JComboBox<>(ore);

        init(true);
    }

    public ReservationDialog(Classroom classrooms,int startHour, int endHour) {
        super();
        classroomsComboBox = new JComboBox<>();
        this.classroomsComboBox.addItem(classrooms);
        startHourComboBox = new JComboBox<>(new String[]{startHour + ":00"});
        endHourComboBox = new JComboBox<>(new String[]{endHour + ":00"});

        init(false);
        
    }

    public void init(boolean isEditable){
        setLayout(new GridLayout(7, 2));

        // Configurazione del date picker
        UtilDateModel model = new UtilDateModel();
        LocalDate now = LocalDate.now();
        model.setDate(now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth());
        model.setSelected(true); // Imposta la data corrente come selezionata
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel,null);


        datePicker.setEnabled(isEditable);
        classroomsComboBox.setEnabled(isEditable);
        startHourComboBox.setEnabled(isEditable);
        endHourComboBox.setEnabled(isEditable);
        // Configurazione delle combo box per l'ora

        nameField = new JTextField();
        descriptionField = new JTextField();
        if(isEditable)
            submitButton = new JButton("Aggiungi Prenotazione");
        else //TODO: verifica che il bottone Modifica Prenotazione funziona
            submitButton = new JButton("Modifica Prenotazione");

        // Aggiunta dei componenti al pannello
        add(new JLabel("Aula:"));
        add(classroomsComboBox);
        add(new JLabel("Data:"));
        add(datePicker);
        add(new JLabel("Ora Inizio (HH):"));
        add(startHourComboBox);
        add(new JLabel("Ora Fine (HH):"));
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

    public void addActionListener(ActionListener listener){
        submitButton.addActionListener(listener);
    }

    public Reservation getSelectedReservation(){
        LocalDate date = LocalDate.of(datePicker.getModel().getYear(), datePicker.getModel().getMonth() + 1, datePicker.getModel().getDay());
        String startHourString = ((String) startHourComboBox.getSelectedItem()).split(":")[0];
        String endHourString = ((String) endHourComboBox.getSelectedItem()).split(":")[0];
        int startHour = Integer.parseInt(startHourString);
        int endHour = Integer.parseInt(endHourString);
        String name = nameField.getText();
        String description = descriptionField.getText();
        return new Reservation(date, startHour, endHour, name, description);
    }
    public int getSelectedClassNumber(){
        return ((Classroom) classroomsComboBox.getSelectedItem()).getNumber();
    }


}
