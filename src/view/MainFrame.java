package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * MainFrame è la classe che rappresenta la finestra principale dell'applicazione.
 */
public class MainFrame extends JFrame implements WindowListener{
    private final TablePanel tablePanel;
    private final MenuPanel menuPanel;

    //serve per chiudere la finestra
    private ActionListener closeListener;

    public MainFrame(TablePanel tp) {
        super("Gestore di Prenotazioni");

        this.tablePanel = tp;
        this.menuPanel = new MenuPanel();

        setupFrame();
        setupComponents();
        
        this.setVisible(true);

        this.addWindowListener(this);
    }
    /**
     * Imposta le proprietà della finestra principale.
     */
    private void setupFrame() {
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
    }
    /**
     *  Aggiunge i componenti alla finestra principale.
     */
    private void setupComponents() {
        this.add(tablePanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(menuPanel);
        this.add(southPanel, BorderLayout.SOUTH);
    }
    /**
     * Imposta il listener per i bottoni della finestra principale.
     * @param listener
     */
    public void setActionListener(ActionListener listener) {
        menuPanel.setSaveButtonListener(listener);
        menuPanel.setAddButtonListener(listener);
        this.closeListener = listener;
    }
    /**
     * Restituisce il comando associato al bottone di salvataggio.
     * @return
     */
    public String getSaveButtonCommand() {
        return menuPanel.getSaveButtonCommand();
    }
    /**
     * Restituisce il comando associato al bottone di aggiunta.
     * @return
     */
    public String getAddButtonCommand() {
        return menuPanel.getAddButtonCommand();
    }

    /**
     * Aggiorna la tabella delle prenotazioni.
     */
    public void updateTable() {
        tablePanel.updateTable();
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }


    @Override
    public void windowClosing(WindowEvent e) {
        if (closeListener != null) {
            closeListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "SaveAndClose"));
        }
    }
    /**
     * Restituisce il comando associato alla chiusura della finestra.
     * @return
     */
    public String getCloseCommand() {
        return "SaveAndClose";
    }

    @Override public void windowOpened(WindowEvent e) {}
    @Override public void windowClosed(WindowEvent e) {}
    @Override public void windowIconified(WindowEvent e) {}
    @Override public void windowDeiconified(WindowEvent e) {}
    @Override public void windowActivated(WindowEvent e) {}
    @Override public void windowDeactivated(WindowEvent e) {}
}
