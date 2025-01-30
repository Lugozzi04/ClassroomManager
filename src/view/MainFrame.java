package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
public class MainFrame extends JFrame {

    private final TablePanel tablePanel;
    private final MenuPanel menuPanel;

    public MainFrame(TablePanel tp) {
        super("Gestore di Prenotazioni");
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        this.menuPanel = new MenuPanel();
        this.tablePanel = tp;

        addMenuPanel();
        addTablePanel();

        this.setVisible(true);
    }

    private void addTablePanel() {
        this.add(tablePanel, BorderLayout.CENTER);
    }

    private void addMenuPanel() {
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(menuPanel);
        this.add(southPanel, BorderLayout.SOUTH);
    }

    public void setActionListener(ActionListener listener) {
        menuPanel.setTableButtonListener(listener);
        menuPanel.setSaveButtonListener(listener);
        menuPanel.setAddButtonListener(listener);
        //tablePanel.setDateChangeListener(listener);
    }

    public void updateTable() {
        tablePanel.updateTable();
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
