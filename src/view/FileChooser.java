package view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Classe che permette di selezionare un file da aprire
 * @param fileName nome del file di default
 */
public class FileChooser extends JOptionPane{

    private String fileName;
    /**
     * Costruttore della classe
     * @param Default
     */
    public FileChooser(String Default) {
        super();
        fileName = Default;
    }
    /**
     * Costruttore della classe
     */
    public FileChooser() {
        super();
        fileName = "";
    }
    /**
     * Metodo che permette di selezionare un file da aprire tramite una finestra di dialogo
     * @return
     */
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
    /**
     * Metodo che permette di selezionare un file da salvare tramite una finestra di dialogo
     * @return
     */
    public String getDefaultFileName() {
        return fileName;
    }
    /**
     * Metodo che permette di selezionare un file da salvare tramite una finestra di dialogo
     * @param fileName
     */
    public void setDefaultFileName(String fileName) {
        this.fileName = fileName;
    }
}
