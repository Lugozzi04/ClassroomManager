package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.entities.Classroom;
import view.JDialogReservation;
import view.JPaneFileChooser;
import view.MainFrame;



public class Controller implements ActionListener {
    private MainFrame mainFrame;
    private String fileName;
    private ModelManager modelManager;

    public Controller() {
        JPaneFileChooser fileChooser = new JPaneFileChooser("Reservations.txt");
        this.fileName = fileChooser.chooseFile();
        modelManager = new ModelManager(fileName);
        this.mainFrame = new MainFrame();
        JDialogReservation<Classroom> addPane= new JDialogReservation<>(modelManager.getClassrooms());
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }



}
