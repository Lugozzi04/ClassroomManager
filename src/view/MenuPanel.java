package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

    private final JButton saveButton;
    private final JButton addButton;
    private static final Dimension BUTTON_SIZE = new Dimension(120, 50);

    public MenuPanel() {
        // Imposta il layout del pannello
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Crea i bottoni
        saveButton = createButton("Salva");
        addButton = createButton("Aggiungi");

        // Aggiungi i bottoni al pannello
        this.add(saveButton);
        this.add(addButton);
        
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(BUTTON_SIZE);
        return button;
    }


    public void setSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public void setAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public String getSaveButtonCommand() {
        return saveButton.getActionCommand();
    }

    public String getAddButtonCommand() {
        return addButton.getActionCommand();
    }


    
}