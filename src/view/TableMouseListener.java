package view;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.JTable;

import org.jdatepicker.impl.UtilDateModel;

import model.entities.*;
import control.ModelManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * TableMouseListener è una classe che implementa un MouseListener per la tabella
 */
public class TableMouseListener extends MouseAdapter implements ActionListener {

    private JTable table;
    private ModelManager modelManager;
    private UtilDateModel model;
    private ReservationDialog dialog;
    private TablePanel tablePanel;
    private LocalDate selectedDate;
    private int[][] classNumber;
    private ShowDialog showDialog;

    private static final int NUMBER_OR_ROWS = 10;

    /**
     * Costruttore della classe TableMouseListener
     * @param tablePanel
     * @param table
     * @param modelManager
     * @param model
     */
    public TableMouseListener(TablePanel tablePanel, JTable table, ModelManager modelManager, UtilDateModel model) {
        this.table = table;
        this.modelManager = modelManager;
        this.model = model;
        this.tablePanel = tablePanel;
        this.classNumber = modelManager.getClassNumberMatrix(NUMBER_OR_ROWS);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = getSelectedRow(e);
        int col = getSelectedColumn(e);

        // Verifica se la cella è valida
        if (row == -1 || col == -1 || col == 0) {
            return;  // Se la cella non è valida o è nella colonna dell'orario, non fare nulla
        }

        String cellValue = (String) table.getValueAt(row, col);

        if (!cellValue.isEmpty()) {
            // Se la cella contiene un nome, è una prenotazione, mostra i dettagli
            showReservationDetails(row, col);
        } else {
            // Altrimenti, mostra i dettagli dell'aula
            showClassroomDetails(row, col);
        }
    }
    /**
     * Metodo che restituisce la riga selezionata
     * @param e
     * @return
     */
    private int getSelectedRow(MouseEvent e) {
        return table.rowAtPoint(e.getPoint());
    }
    /**
     * Metodo che restituisce la colonna selezionata
     * @param e
     * @return
     */
    private int getSelectedColumn(MouseEvent e) {
        return table.columnAtPoint(e.getPoint());
    }
    /**
     * Metodo che restituisce la data selezionata
     * @return
     */
    private LocalDate getSelectedDate() {
        return LocalDate.of(model.getYear(), model.getMonth() + 1, model.getDay());
    }
    /**
     * Metodo che mostra i dettagli della prenotazione
     * @param row
     * @param col
     */
    private void showReservationDetails(int row, int col) {
        selectedDate = getSelectedDate();
        Reservation reservation = modelManager.getReservation(selectedDate, classNumber[row][col], row + 8);
        showDialog = new ShowDialog(reservation);
        showDialog.addShowListeners(this);
        showDialog.setVisible(true);
    }
    /**
     * Metodo che mostra i dettagli dell'aula
     * @param row
     * @param col
     */
    private void showClassroomDetails(int row, int col) {
        Classroom classroom = modelManager.getClassroom(classNumber[row][col]);
        showDialog = new ShowDialog(classroom);
        showDialog.setVisible(true);
    }
    /**
     * Metodo che gestisce gli eventi
     */
    @Override   
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals(showDialog.getEditButtonCommand())) {
            openEditReservationDialog();
        } else if (e.getActionCommand().equals(showDialog.getRemoveButtonCommand())) {
            removeReservation();
            showDialog.dispose();
        } else if (e.getActionCommand().equals(dialog.getSubmitButtonCommand())) {
            modifyReservation();
            dialog.dispose();
            showDialog.dispose();
        }
    }
    /**
     * Metodo che apre il dialog per modificare la prenotazione
     */
    private void openEditReservationDialog() {
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        Reservation reservation = modelManager.getReservation(selectedDate, classNumber[row][col], row + 8);
        if(reservation == null){
            JOptionPane.showMessageDialog(null, "Prenotazione non trovata", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dialog = new ReservationDialog(modelManager.getClassroom(classNumber[row][col]), reservation.getStartHour(), reservation.getEndHour(),reservation.getDate(),reservation.getName(),reservation.getReason());
        dialog.setVisible(true);
        dialog.addActionListener(this);
    }
    /**
     * Metodo che modifica la prenotazione
     */
    private void modifyReservation() {
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        Reservation oldReservation = modelManager.getReservation(selectedDate, classNumber[row][col], row + 8);
        Reservation newReservation = dialog.getSelectedReservation();

        if (newReservation == null) {
            JOptionPane.showMessageDialog(null, "La nuova prenotazione non è valida", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(!modelManager.updateReservation(oldReservation, newReservation, classNumber[row][col])){
            JOptionPane.showMessageDialog(null, "L'aula è già occupata per le ore scelte", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        tablePanel.updateTable();
    }
    /**
     * Metodo che rimuove la prenotazione
     */
    private void removeReservation() {
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        Reservation reservation = modelManager.getReservation(selectedDate, classNumber[row][col], row + 8);

        if (modelManager.removeReservation(reservation, classNumber[row][col])) {
            System.out.println("Reservation removed: " + reservation);
        }

        tablePanel.updateTable();
    }

    
}


