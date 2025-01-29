package view;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class JPaneFileChooser extends JOptionPane{

    private String fileName;

    public JPaneFileChooser(String Default) {
        super();
        fileName = Default;
    }

    public JPaneFileChooser() {
        super();
        fileName = "";
    }

    public String chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleziona il file di prenotazioni esistente");

        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            return fileToOpen.getAbsolutePath();
        } else {
            JOptionPane.showMessageDialog(null,"file di default: "+ fileName, "Default", JOptionPane.DEFAULT_OPTION);
            return fileName;
        }

    }

    public String getDefaultFileName() {
        return fileName;
    }

    public void setDefaultFileName(String fileName) {
        this.fileName = fileName;
    }
}
