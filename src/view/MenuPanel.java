package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
/**
 * Classe che rappresenta il pannello di menu dell'applicazione
 */
public class MenuPanel extends JPanel {

    private final JButton saveButton;
    private final JButton addButton;
    private static final Dimension BUTTON_SIZE = new Dimension(120, 50);
    /**
     * Costruttore del pannello di menu dell'applicazione
     */
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
    /**
     * Metodo che crea un bottone con il testo passato come parametro
     * @param text
     * @return
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(BUTTON_SIZE);
        return button;
    }

    /**
     * Metodo che imposta il listener del bottone di salvataggio
     * @param listener
     */
    public void setSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
    /**
     * Metodo che imposta il listener del bottone di aggiunta
     * @param listener
     */
    public void setAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }
    /**
     * Metodo che restituisce il comando del bottone di salvataggio
     * @return
     */
    public String getSaveButtonCommand() {
        return saveButton.getActionCommand();
    }
    /**
     * Metodo che restituisce il comando del bottone di aggiunta
     */
    public String getAddButtonCommand() {
        return addButton.getActionCommand();
    }


    
}