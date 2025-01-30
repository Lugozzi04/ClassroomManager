package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

    private final JButton printButton;
    private final JButton saveButton;
    private final JButton addButton;

    public MenuPanel() {
        // Imposta il layout del pannello
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Crea i bottoni con dimensioni fisse
        Dimension buttonSize = new Dimension(120, 50);

        // Crea i bottoni
        printButton = new JButton("Stampa");
        printButton.setPreferredSize(buttonSize);
        saveButton = new JButton("Salva");
        saveButton.setPreferredSize(buttonSize);
        addButton = new JButton("Aggiungi");
        addButton.setPreferredSize(buttonSize);

        // Aggiungi i bottoni al pannello
        this.add(printButton);
        this.add(saveButton);
        this.add(addButton);
        
    }

    public void setTableButtonListener(ActionListener listener) {
        printButton.addActionListener(listener);
    }

    public void setSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public void setAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }
}