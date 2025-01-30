package view;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.entities.Classroom;
import model.entities.Reservation;

public class ShowDialog extends JDialog {

    private JButton editButton;
    private JButton removeButton;

    public ShowDialog(Classroom classroom) {
        super();
        setTitle("Classroom Details");
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Disponibile per la prenotazione"));
        panel.add(new JLabel("Classroom Details"));
        panel.add(new JLabel("Numero: " + classroom.getNumber()));
        panel.add(new JLabel("Tipo: " + classroom.getType()));
        panel.add(new JLabel("Capacita: " + classroom.getCapacity()));
        panel.add(new JLabel("Info: " + classroom.getInfo()));
        
        add(panel, BorderLayout.CENTER);
        setSize(300, 200); 
        setLocationRelativeTo(null);
        }
    
    public ShowDialog(Reservation reservation) {
        super();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Reservation Details"));
        panel.add(new JLabel("Data: " + reservation.getDate()));
        panel.add(new JLabel("Start Hour: " + reservation.getStartHour()));
        panel.add(new JLabel("End Hour: " + reservation.getEndHour()));
        panel.add(new JLabel("Nome: " + reservation.getName()));
        panel.add(new JLabel("Description: " + reservation.getReason()));

        this.editButton = new JButton("Edit");
        this.removeButton = new JButton("Remove");
        panel.add(editButton);
        panel.add(removeButton);
        add(panel, BorderLayout.CENTER);
        setSize(300, 250); // Imposta la dimensione della finestra
        setLocationRelativeTo(null);
        }

    public void addShowListeners(ActionListener listener) {
        System.err.println("Adding listener to Buttons");
        editButton.addActionListener(listener);
        removeButton.addActionListener(listener);
    }

}

