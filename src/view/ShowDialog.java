package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.entities.Classroom;
import model.entities.Reservation;
/**
 * Classe che implementa una finestra di dialogo per visualizzare i dettagli di una aula o di una prenotazione.
 */
public class ShowDialog extends JDialog {

    private JButton editButton;
    private JButton removeButton;

    private static final Dimension WINDOW_SIZE = new Dimension(400, 300);
    /**
     * Costruttore della classe ShowDialog.
     * @param classroom
     */
    public ShowDialog(Classroom classroom) {
        super();
        setTitle("Classroom Details");

        JPanel panel = initClassroomPanel(classroom);
        add(panel, BorderLayout.CENTER);

        setPreferredSize(WINDOW_SIZE); // Imposta dimensioni preferite per la finestra
        setMinimumSize(WINDOW_SIZE); // Impedisce che la finestra si ridimensioni troppo piccola
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Costruttore della classe ShowDialog.
     * @param reservation
     */
    public ShowDialog(Reservation reservation) {
        super();
        setTitle("Reservation Details");

        JPanel panel = initReservationPanel(reservation);
        add(panel, BorderLayout.CENTER);

        setPreferredSize(WINDOW_SIZE); // Imposta dimensioni preferite per la finestra
        setMinimumSize(WINDOW_SIZE); // Impedisce che la finestra si ridimensioni troppo piccola
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Metodo che inizializza il pannello per visualizzare i dettagli di una aula.
     * @param classroom
     * @return
     */
    private JPanel initClassroomPanel(Classroom classroom) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Disponibile per la prenotazione"));
        panel.add(new JLabel("Dettagli sulla classe:"));
        panel.add(new JLabel("Numero: " + classroom.getNumber()));
        panel.add(new JLabel("Tipo: " + classroom.getType()));
        panel.add(new JLabel("Capacità: " + classroom.getCapacity()));
        panel.add(new JLabel("Info: " + classroom.getInfo()));
        panel.add(new JLabel(classroom.ruleForBooking()));
        return panel;
    }
    /**
     * Metodo che inizializza il pannello per visualizzare i dettagli di una prenotazione.
     * @param reservation
     * @return
     */
    private JPanel initReservationPanel(Reservation reservation) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("dettagli sulla prenotazione:"));
        panel.add(new JLabel("Data: " + reservation.getDate()));
        panel.add(new JLabel("Ora Inizio: " + reservation.getStartHour()));
        panel.add(new JLabel("Ora Fine: " + reservation.getEndHour()));
        panel.add(new JLabel("Nome: " + reservation.getName()));
        panel.add(new JLabel("Descrizione: " + reservation.getReason()));

        editButton = new JButton("Modifica");
        removeButton = new JButton("Rimuovi");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);

        panel.add(buttonPanel);

        return panel;
    }
    /**
     * Metodo che restituisce il comando del bottone per modificare una prenotazione.
     * @return
     */
    public String getEditButtonCommand() {
        return editButton.getActionCommand();
    }
    /**
     * Metodo che restituisce il comando del bottone per rimuovere una prenotazione.
     * @return
     */
    public String getRemoveButtonCommand() {
        return removeButton.getActionCommand();
    }

    /**
     * Metodo che aggiunge un ascoltatore per i bottoni di modifica e rimozione di una prenotazione.
     * @param listener
     */
    public void addShowListeners(ActionListener listener) {
        if (editButton != null && removeButton != null) {
            editButton.addActionListener(listener);
            removeButton.addActionListener(listener);
        }
    }
}

