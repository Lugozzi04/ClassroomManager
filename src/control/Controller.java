package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.entities.*;
import view.FileChooser;
import view.TablePanel;
import view.MainFrame;
import view.ReservationDialog;



public class Controller implements ActionListener {
    private final MainFrame mainFrame;
    private final ModelManager modelManager;

    
    private ReservationDialog dialog;
    private TablePanel tablePanel;

    public Controller() {
        // Scelta file con fallback su "Reservations.txt"
        FileChooser fileChooser = new FileChooser("Reservations.txt");
        String fileName = fileChooser.chooseFile();

        // Inizializzazione del ModelManager
        this.modelManager = new ModelManager(fileName);

        // Creazione della GUI
        tablePanel = new TablePanel(modelManager);
        this.mainFrame = new MainFrame(tablePanel);

        //Componenti della GUI
        this.dialog = null;

        // Registra gli event listener
        registerListeners();
    }

    private void registerListeners() {
        mainFrame.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Aggiungi":
                openAddReservationDialog();
                break;
            case "Salva":
                saveReservations();
                break;
            case "Stampa":
                
                break;
            case "Aggiungi Prenotazione":
                addReservation();
                break;
            default:
                break;

        }
    }

    private void openAddReservationDialog() {
        dialog = new ReservationDialog(modelManager.getClassrooms());
        dialog.setVisible(true);
        dialog.addActionListener(this);
    }

    private void addReservation() {
        Reservation newReservation = dialog.getSelectedReservation();
        int classNumber = dialog.getSelectedClassNumber();
        if (newReservation != null) {
            modelManager.addReservation(newReservation, classNumber);
            updateTable();
            dialog.dispose();
        }
    }

    private void saveReservations() {
        if (modelManager.save()) {
            mainFrame.showMessage("Prenotazioni salvate con successo.");
        } else {
            mainFrame.showMessage("Errore nel salvataggio delle prenotazioni.");
        }
    }

    private void updateTable() {
        mainFrame.updateTable();
    }
}
