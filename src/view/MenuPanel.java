package view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

    private JButton tabellaButton;
    private JButton modificaButton;
    private JButton aggiungiButton;

    public MenuPanel() {
        // Imposta il layout del pannello
        this.setLayout(new GridLayout(1, 3));

        // Crea i bottoni
        tabellaButton = new JButton("Tabella");
        modificaButton = new JButton("Modifica");
        aggiungiButton = new JButton("Aggiungi");

        // Aggiungi i bottoni al pannello
        this.add(tabellaButton);
        this.add(modificaButton);
        this.add(aggiungiButton);
        
    }

    public void setTabellaButtonListener(ActionListener listener) {
        tabellaButton.addActionListener(listener);
    }

    public void setModificaButtonListener(ActionListener listener) {
        modificaButton.addActionListener(listener);
    }

    public void setAggiungiButtonListener(ActionListener listener) {
        aggiungiButton.addActionListener(listener);
    }
}