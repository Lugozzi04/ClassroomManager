package control;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.entities.Classroom;
import model.entities.Reservation;
import model.services.AutoSave;
import model.services.Cache;
import model.services.CacheLoader;
import model.services.Writer;
import model.struct.ManagerClassReservations;
/**
 * Classe che gestisce la logica di business dell'applicazione e la comunicazione con il livello di persistenza.
 */
public class ModelManager {
    
    private final ManagerClassReservations<LocalDate,Classroom> manager;
    private final Cache cacheReservation;
    private final Writer writerReservation;
    private AutoSave autoSaveThread;
    private final List<Classroom> classrooms;

    private Writer writerTMP;

    private static final String FILE_NAME_CLASSROOMS = "Classrooms.txt";
    private static final String FILE_NAME_TMP="RESERVATIONS_TMP.txt";
    private static final String SPLIT_CARACTER="\\{";
    private static final String FORMAT = "%d{%s";
    
    /**
     * Costruttore della classe ModelManager che inizializza il manager delle prenotazioni e carica la lista delle aule.
     * @param fileName
     */
    public ModelManager(String fileName) {
        this.classrooms = getListClassroom();
        this.manager = new ManagerClassReservations<>(classrooms);
        CacheLoader loader = new CacheLoader(fileName);
        cacheReservation=loader.loadCache();
        writerReservation = new Writer(fileName);
    }
    /**
     * Metodo che restituisce una matrice contentente il numero di ogni classe in data posizione.
     * @param rows
     * @return
     */
    public int[][] getClassNumberMatrix(int rows) {
        int[][] matrix = new int[rows][classrooms.size()+1];
        for (int i = 0; i < rows; i++) {
            matrix[i][0] = -1;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 1; j < classrooms.size()+1; j++) {
                matrix[i][j] = classrooms.get(j-1).getNumber();
            }
        }
        return matrix;
    }
    /**
     * Metodo che aggiunge una prenotazione alla lista delle prenotazioni e al file di cache.
     * @param r
     * @param classroomNumber
     * @return
     */
    public boolean addReservation(Reservation r, int classroomNumber) {
        return manager.addReservation(r.getDate(), classroomNumber, r)&&
                cacheReservation.addLine(String.format(FORMAT, classroomNumber, r.toString()));
    }
    /**
     * Metodo che rimuove una prenotazione dalla lista delle prenotazioni e dal file di cache.
     * @param r
     * @param classroomNumber
     * @return
     */
    public boolean removeReservation(Reservation r, int classroomNumber) { 
        String formattedReservation = String.format(FORMAT, classroomNumber, r.toString());
        int index = cacheReservation.getLineIndex(formattedReservation);
        return manager.removeReservation(r.getDate(), classroomNumber, r) && cacheReservation.removeLine(index);
    }
    /**
     * Metodo che aggiorna una prenotazione nella lista delle prenotazioni e nel file di cache.
     * @param oldR
     * @param newR
     * @param classroomNumber
     * @return
     */
    public boolean updateReservation(Reservation oldR, Reservation newR, int classroomNumber) {
        String formattedOld = String.format(FORMAT, classroomNumber, oldR.toString());
        String formattedNew = String.format(FORMAT, classroomNumber, newR.toString());
        
        int index = cacheReservation.getLineIndex(formattedOld);
        return manager.updateReservation(oldR.getDate(), classroomNumber, oldR, newR) &&
               cacheReservation.removeLine(index) &&
               cacheReservation.addLine(formattedNew);
    }
    /**
     * Metodo che restituisce la lista delle prenotazioni di una determinata aula.
     * @param classroomNumber
     * @return
     */
    public boolean finalSave(){
        return writerReservation.overWriteFile(cacheReservation.getCache());
    }
    /**
     * Metodo che salva le prenotazioni in un file temporaneo.
     * @return
     */
    public boolean saveTMP(){
        return writerTMP.overWriteFile();
    }
    /**
     * Metodo che salva le prenotazioni in un file temporaneo.
     * @param intervalSeconds
     * @return
     */
    public boolean autoSave(int intervalSeconds){
        try{
        if (autoSaveThread != null) {
            autoSaveThread.stopAutoSave(); // Ferma il vecchio thread
        }
        writerTMP=new Writer(FILE_NAME_TMP, cacheReservation);
        autoSaveThread = new AutoSave(writerTMP, intervalSeconds);
        autoSaveThread.start();
        }catch(Exception e){
            return false;
        }
        return true;
    }
    /**
     * Metodo che restituisce la lista delle prenotazioni di una determinata aula.
     * @param classroomNumber
     * @return
     */
    public Reservation getReservation(LocalDate date, int classroomNumber, int hour) {
        return manager.getReservation(date, classroomNumber, hour);
    }
    /**
     * Metodo che restituisce la lista delle prenotazioni di una determinata aula.
     * @param classroomNumber
     * @return
     */
    public Classroom getClassroom(int classNumber) {
        return manager.getClassroom(classNumber);
    }
    /**
     * funzione di inizializzazione delle aule
     * @return
     */
    private List<Classroom> getListClassroom(){
        List<Classroom> classes = new ArrayList<>();
        CacheLoader loader = new CacheLoader(FILE_NAME_CLASSROOMS);
        Cache cacheClass = loader.loadCache();

        for (int i = 0; i < cacheClass.getSize(); i++) {
            String line = cacheClass.getLine(i);      
            classes.add(Classroom.stringToClassroom(line));
        }
        return classes;
    }
    /**
     * funzione di inizializzazione delle prenotazioni
     * @return
     */
    public boolean initReservation(){
        try {
            
            for (int i = 0; i < cacheReservation.getSize(); i++) {
                String line = cacheReservation.getLine(i);      
                String[] parts = line.split(SPLIT_CARACTER);
                Reservation r = Reservation.stringToReservation(parts[1]);
                if(r==null){
                    return false;
                }
                manager.addReservation(r.getDate(),Integer.parseInt(parts[0]),r);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
        
    }
    /**
     * Metodo che restituisce la lista delle aule.
     * @return classrooms
     */
    public List<Classroom> getClassrooms() {
        return classrooms;
    }
}
