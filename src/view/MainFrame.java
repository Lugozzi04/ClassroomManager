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

public class MainFrame extends JFrame implements WindowListener{
    private final TablePanel tablePanel;
    private final MenuPanel menuPanel;

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

    private void setupFrame() {
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
    }

    private void setupComponents() {
        this.add(tablePanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(menuPanel);
        this.add(southPanel, BorderLayout.SOUTH);
    }

    public void setActionListener(ActionListener listener) {
        menuPanel.setSaveButtonListener(listener);
        menuPanel.setAddButtonListener(listener);
        this.closeListener = listener;
    }

    public String getSaveButtonCommand() {
        return menuPanel.getSaveButtonCommand();
    }

    public String getAddButtonCommand() {
        return menuPanel.getAddButtonCommand();
    }


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
