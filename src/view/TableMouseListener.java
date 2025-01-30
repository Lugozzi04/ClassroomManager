package view;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JTable;

import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import model.entities.*;
import control.ModelManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableMouseListener extends MouseAdapter implements ActionListener {

    private JTable table;
    private ModelManager modelManager;
    private List<Classroom> classrooms;
    private UtilDateModel model;
    private ReservationDialog dialog;
    private TablePanel tablePanel;
    private LocalDate selectedDate;
    private int[][] classNumber;

    private ShowDialog jpaneShow;

    public TableMouseListener(TablePanel JPanel, JTable table, ModelManager modelManager, UtilDateModel model) {
        this.table = table;
        this.modelManager = modelManager;
        this.classrooms = modelManager.getClassrooms();
        this.model = model;
        this.tablePanel = JPanel;
    }

    public void setClassNumber(int[][] classNumber) {
        this.classNumber = classNumber;

    }

    private int getSelectedRow(MouseEvent e) {
        return table.rowAtPoint(e.getPoint());
    }
    private int getSelectedColumn(MouseEvent e) {
        return table.columnAtPoint(e.getPoint());
    }
    private LocalDate getSelectedDate(){
        return LocalDate.of(model.getYear(), model.getMonth() + 1, model.getDay());
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = getSelectedRow(e); // Ottieni la riga cliccata
        int col = getSelectedColumn(e); // Ottieni la colonna cliccata

        // Verifica che la cella cliccata sia valida
        if (row == -1 || col == -1) {
            return;
        }

        // Se la cella cliccata è nella prima colonna (Orario), non fare nulla
        if (col == 0) {
            return;
        }

        String cellValue = (String) table.getValueAt(row, col);

        if ("Prenotato".equals(cellValue)) {
            // La cella è rossa, quindi mostra la finestra di dettaglio della prenotazione
            selectedDate = getSelectedDate();
            Reservation reservation = modelManager.getReservation(selectedDate, classNumber[row][col], row + 8);
            jpaneShow = new ShowDialog(reservation); // Passa il JPanelTable come proprietario
            jpaneShow.addShowListeners(this);
            jpaneShow.setVisible(true);
        } else {
            // Altrimenti, mostra i dettagli dell'aula
            Classroom classroom = modelManager.getClassroom(classNumber[row][col]);
            jpaneShow = new ShowDialog(classroom); // Passa il JPanelTable come proprietario
            jpaneShow.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        if(e.getActionCommand().equals("Edit")){
            openEditReservationDialog();
            jpaneShow.dispose();

        } else if(e.getActionCommand().equals("Remove")){
            removeReservation();
            jpaneShow.dispose();
            
        }else if(e.getActionCommand().equals("Aggiungi Prenotazione")){
            addReservation();
            jpaneShow.dispose();
        }else if(e.getActionCommand().equals("Modifica Prenotazione")){
            modifyReservation();
            jpaneShow.dispose();
        }
    }

    private void openEditReservationDialog() {
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        Reservation reservation = modelManager.getReservation(selectedDate, classNumber[row][col], row + 8);
        dialog = new ReservationDialog(modelManager.getClassroom(classNumber[row][col]), reservation.getStartHour(), reservation.getEndHour());
        dialog.setVisible(true);
        dialog.addActionListener(this);
    }

    private void modifyReservation() {
        LocalDate selectedDate = LocalDate.of(model.getYear(), model.getMonth() + 1, model.getDay());
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        Reservation oldReservation= modelManager.getReservation(selectedDate, classNumber[row][col], row + 8);
        Reservation newReservation = dialog.getSelectedReservation();
        modelManager.updateReservation(oldReservation, newReservation, classNumber[row][col]);
        tablePanel.updateTable();
    }

    private void addReservation() {
        Reservation newReservation = dialog.getSelectedReservation();
        int classNumber = dialog.getSelectedClassNumber();
        if (newReservation != null) {
            modelManager.addReservation(newReservation, classNumber);
            tablePanel.updateTable();
        }
    }
    private void removeReservation(){
        LocalDate selectedDate = LocalDate.of(model.getYear(), model.getMonth() + 1, model.getDay());
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        Reservation r = modelManager.getReservation(selectedDate, classNumber[row][col], row + 8);
        modelManager.removeReservation(r, classNumber[row][col]);
 
        tablePanel.updateTable();
    }

    
}


