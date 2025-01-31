package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.entities.*;
import view.FileChooser;
import view.MainFrame;
import view.ReservationDialog;
import view.TablePanel;



public class Controller implements ActionListener {
    private final MainFrame mainFrame;
    private final ModelManager modelManager;
    private final String fileName;
    private final TablePanel tablePanel;

    private ReservationDialog dialog;

    private static final int AUTO_SAVE_INTERVAL = 10;
    private static final String DEFAULT_FILE = "Reservations.txt";

    public Controller() {
        this.fileName = initializeFile();
        this.modelManager = new ModelManager(fileName);
        this.tablePanel = new TablePanel(modelManager);
        this.mainFrame = new MainFrame(tablePanel);
        this.dialog = null;

        registerListeners();
        modelManager.autoSave(AUTO_SAVE_INTERVAL);
    }

    private String initializeFile() {
        FileChooser fileChooser = new FileChooser(DEFAULT_FILE);
        String chosenFile = fileChooser.chooseFile();
        validateFile(chosenFile);
        return chosenFile;
    }

    private void validateFile(String fileName){
        String[] f=fileName.split("\\.");
        if(f.length!=2 || !f[1].equals("txt")){
            JOptionPane.showMessageDialog(null, "Il file selezionato non è un file di testo (.txt).", "Errore", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void registerListeners() {
        mainFrame.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();
        if (command.equals(mainFrame.getAddButtonCommand())) {
            openAddReservationDialog();
        } else if (command.equals(mainFrame.getSaveButtonCommand())) {
            saveReservations();
        } else if (command.equals(mainFrame.getCloseCommand())) {
            saveReservations();
            System.exit(0);
        } else if (command.equals(dialog.getSubmitButtonCommand())) {
            addReservation();
        } 
    }



    private void openAddReservationDialog() {
        dialog = new ReservationDialog(modelManager.getClassrooms());
        dialog.setVisible(true);
        dialog.addActionListener(this);
    }

    private void addReservation() {
        if (dialog == null) return;

        Reservation newReservation = dialog.getSelectedReservation();
        int classNumber = dialog.getSelectedClassNumber();

        if (newReservation != null) {
            modelManager.addReservation(newReservation, classNumber);
            updateTable();
            dialog.dispose();
        }
    }

    private void saveReservations() {
        int response = JOptionPane.showConfirmDialog(mainFrame, "Vuoi sovrascrivere il file "+fileName+"?", "Conferma Salvataggio", JOptionPane.YES_NO_OPTION);
        
        if (response != JOptionPane.YES_OPTION) {
            mainFrame.showMessage("Prenotazioni non salvate.");
            return;
        }

        if (modelManager.finalSave()) {
            mainFrame.showMessage("Prenotazioni salvate con successo.");
        } else {
            mainFrame.showMessage("Errore nel salvataggio delle prenotazioni.");
        }

    }

    private void updateTable() {
        mainFrame.updateTable();
    }
}
