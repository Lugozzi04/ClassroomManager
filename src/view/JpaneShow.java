package view;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.entities.Classroom;
import model.entities.Reservation;

public class JpaneShow extends JOptionPane {

    public JpaneShow(Classroom classroom) {
        super();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Classroom Details"));
        panel.add(new JLabel("Numero: " + classroom.getNumber()));
        panel.add(new JLabel("Tipo: " + classroom.getType()));
        panel.add(new JLabel("Capacita: " + classroom.getCapacity()));
        panel.add(new JLabel("Info: " + classroom.getInfo()));
        
        showMessageDialog(null, panel, "Classroom Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public JpaneShow(Reservation reservation) {
        super();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Reservation Details"));
        panel.add(new JLabel("Data: " + reservation.getDate()));
        panel.add(new JLabel("Start Hour: " + reservation.getStartHour()));
        panel.add(new JLabel("End Hour: " + reservation.getEndHour()));
        panel.add(new JLabel("Nome: " + reservation.getName()));
        panel.add(new JLabel("Description: " + reservation.getReason()));

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> {
            // Implement the edit functionality here
            JOptionPane.showMessageDialog(null, "Edit button clicked");
        });
        panel.add(editButton);

        showMessageDialog(null, panel, "Reservation Details", JOptionPane.INFORMATION_MESSAGE);
    }
}

