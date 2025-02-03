package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.entities.Reservation;
import view.FileChooser;
import view.MainFrame;
import view.ReservationDialog;
import view.TablePanel;


/**
 * Classe che gestisce la logica di business dell'applicazione e la comunicazione con il livello di presentazione.
 */
public class Controller implements ActionListener {
    private final MainFrame mainFrame;
    private final ModelManager modelManager;
    private final String fileName;
    private final TablePanel tablePanel;

    private ReservationDialog dialog;

    private static final int AUTO_SAVE_INTERVAL = 10;
    private static final String DEFAULT_FILE = "Reservations.txt";
    /**
     * Costruttore della classe Controller che inizializza il modelManager e la tabella.
    */
    public Controller() {
        this.fileName = initializeFile();
        this.modelManager = new ModelManager(fileName);

        if(!modelManager.initReservation()){
            JOptionPane.showMessageDialog(null, "Errore nel caricamento delle prenotazioni. Probabile causa: File di formato invalido", "Errore", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        
        this.tablePanel = new TablePanel(modelManager);
        this.mainFrame = new MainFrame(tablePanel);
        this.dialog = null;

        registerListeners();
        if(!modelManager.autoSave(AUTO_SAVE_INTERVAL)){
            JOptionPane.showMessageDialog(null, "Errore nel salvataggio automatico delle prenotazioni.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Metodo che inizializza il file di cache.
     * @return il nome del file di cache.
     */
    private String initializeFile() {
        FileChooser fileChooser = new FileChooser(DEFAULT_FILE);
        String chosenFile = fileChooser.chooseFile();
        validateFile(chosenFile);
        return chosenFile;
    }
    /**
     * Metodo che controlla se il file selezionato è un file di testo.
     * @param fileName
     */
    private void validateFile(String fileName){
        String[] f=fileName.split("\\.");
        if(f.length!=2 || !f[1].equals("txt")){
            JOptionPane.showMessageDialog(null, "Il file selezionato non è un file di testo (.txt).", "Errore", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    /**
     * Metodo che registra i listener per i pulsanti della finestra principale.
     * @param mainFrame
     */
    private void registerListeners() {
        mainFrame.setActionListener(this);
    }
    /**
     * Metodo che gestisce gli eventi generati dai pulsanti della finestra principale.
     * @param e
     */
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


    /**
     * Metodo che apre la finestra di dialogo per l'aggiunta di una prenotazione.
     */
    private void openAddReservationDialog() {
        dialog = new ReservationDialog(modelManager.getClassrooms());
        dialog.setVisible(true);
        dialog.addActionListener(this);
    }
    /**
     * Metodo che aggiunge una prenotazione alla lista delle prenotazioni e alla tabella.
     */
    private void addReservation() {
        if (dialog == null) return;

        Reservation newReservation = dialog.getSelectedReservation();
        int classNumber = dialog.getSelectedClassNumber();

        if (newReservation != null) {
            if(!modelManager.addReservation(newReservation, classNumber)){
                JOptionPane.showMessageDialog(null, "Aula non prenotabile ", "Errore", JOptionPane.ERROR_MESSAGE);
            }
            updateTable();
            dialog.dispose();
        }
    }
    /**
     * Metodo che salva le prenotazioni nel file di cache.
     */
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
    /**
     * Metodo che aggiorna la tabella delle prenotazioni.
     */
    private void updateTable() {
        mainFrame.updateTable();
    }
}
