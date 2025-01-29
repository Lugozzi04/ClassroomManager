package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


public final class JDialogReservation<C> extends JDialog implements ActionListener {
    
    JComboBox<C> classroomsComboBox;
    private JDatePickerImpl datePicker;
    private JComboBox<String> startHourComboBox;
    private JComboBox<String> endHourComboBox;
    private JTextField nameField;
    private JTextField descriptionField;

    public JDialogReservation(List<C> classrooms) {
        super();
        classroomsComboBox = new JComboBox<>();
        for(C classroom : classrooms) {
            this.classroomsComboBox.addItem(classroom);
        }

        String[] ore = {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};
        startHourComboBox= new JComboBox<>(ore);
        endHourComboBox = new JComboBox<>(ore);

        init();
    }

    public JDialogReservation(C classrooms,int startHour, int endHour) {
        super();
        classroomsComboBox = new JComboBox<>();
        this.classroomsComboBox.addItem(classrooms);
        startHourComboBox = new JComboBox<>(new String[]{startHour + ":00"});
        endHourComboBox = new JComboBox<>(new String[]{endHour + ":00"});
        classroomsComboBox.setEnabled(false);
        startHourComboBox.setEnabled(false);
        endHourComboBox.setEnabled(false);
        init();
    }

    public void init(){
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
        datePicker = new JDatePickerImpl(datePanel, null);

        // Configurazione delle combo box per l'ora

        nameField = new JTextField();
        descriptionField = new JTextField();
        JButton submitButton = new JButton("Aggiungi Prenotazione");

        submitButton.addActionListener(this);

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



    @Override
    public void actionPerformed(ActionEvent e) {

        /*if(e.getActionCommand().equals("Aggiungi Prenotazione")) {
            C aula = (C) classroomsComboBox.getSelectedItem();
            java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
            String dataString = formatDate(selectedDate);
            LocalDate data = LocalDate.parse(dataString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int oraInizio = Integer.parseInt(oraInizioComboBox.getSelectedItem().toString().split(":")[0]);
            int oraFine = Integer.parseInt(oraFineComboBox.getSelectedItem().toString().split(":")[0]);
            String nome = nomeField.getText();
            String descrizione = descrizioneField.getText();

            // Creazione di un oggetto prenotazione
            // Prenotazione prenotazione = new Prenotazione(classroom, date, startHour, endHour, name, description);
            // Aggiunta della prenotazione al database
            // Database.getInstance().addPrenotazione(prenotazione);
            // Chiudi la finestra
            dispose();
        }*/
    }


}
