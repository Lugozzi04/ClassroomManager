package model.struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.entities.Classroom;
import model.entities.Reservation;

/**
 * Classe che gestisce le prenotazioni delle aule.
 * @param <D> Tipo della chiave per la struttura dati che contiene le aule e prenotazioni
 * @param <C> Tipo della classe che rappresenta le aule
 */
public class ManagerClassReservations <D,C extends Classroom> {

    private final Map<Integer, C> classrooms; // Mappa delle aule
    private final Map<D, Map<Integer, List<Reservation>>> reservations; // Mappa delle prenotazioni

    private final static int DEFAULT_INT_ERROR = -1;

    /**
     * Costruttore della classe.
     * @param classrooms Lista delle aule
     */
    public ManagerClassReservations(List<C> classrooms) {
        this.reservations = new HashMap<>();
        this.classrooms = initClassrooms(classrooms);
    }

    /**
     * Inizializza la mappa delle aule.
     * @param classrooms Lista delle aule
     * @return Mappa delle aule
     */
    private Map<Integer, C> initClassrooms(List<C> classrooms) {
        Map<Integer, C> map = new HashMap<>();
        for (C c : classrooms) {
            map.put(c.getNumber(), c);
        }
        return map;
    }
    /**
     * Restituisce il valore della chiave associata all'aula.
     * @param classroom
     * @return
     */
    public int getClassroomNumber(C classroom){
        for (Map.Entry<Integer, C> entry : classrooms.entrySet()) {
            if (entry.getValue().equals(classroom)) {
                // Trova la Classroom corrispondente 
                return entry.getKey(); // la chiave associata
            }
        }
        return DEFAULT_INT_ERROR; // Non trovata
    }
    /**
     * Controlla se l'aula è presente nella mappa delle aule.
     * @param classroomNumber
     * @return
     */
    private boolean isClassroom(int classroomNumber) {
        return classrooms.containsKey(classroomNumber);
    }

    /**
     * Restituisce l'aula associata alla key
     * @param classroomNumber
     * @return
     */
    public Classroom getClassroom(int classroomNumber) {
        return classrooms.get(classroomNumber);
    }
    /**
     * Restituisce la prenotazione associata alla key e all'aula
     * @param key
     * @param classroomNumber
     * @param hourToCheck
     * @return
     */
    public Reservation getReservation(D key, int classroomNumber, int hourToCheck) {
        if (!reservations.containsKey(key) || !reservations.get(key).containsKey(classroomNumber)) {
            return null;
        }
        for (Reservation r : reservations.get(key).get(classroomNumber)) {

            for(int hour : r.getHours()){
                if (hourToCheck == hour) {
                    return r;
                }
            }
        }
        return null;
    }

    /**
     * aggiunge una prenotazione alla mappa delle prenotazioni
     * @param key
     * @param classroomNumber
     * @param reservation
     * @return
     */
    public boolean addReservation(D key, int classroomNumber, Reservation reservation) {
        if (!isClassroom(classroomNumber) || isOverlapping(key, classroomNumber, reservation)) {
            return false;
        }
        
        C classroom = classrooms.get(classroomNumber);
        if(!classroom.isBookable(reservation.getEndHour()-reservation.getStartHour())){
            return false;
        }

        reservations.putIfAbsent(key, new HashMap<>());
        reservations.get(key).putIfAbsent(classroomNumber, new ArrayList<>());
        
        

        return reservations.get(key).get(classroomNumber).add(reservation);
    }

    /**
     * Controlla se la prenotazione si sovrappone ad altre prenotazioni
     * @param key
     * @param classroomNumber
     * @param reservation
     * @return
     */
    private boolean isOverlapping(D key, int classroomNumber, Reservation reservation) {
        if (!reservations.containsKey(key)) {
            return false;
        }
        if (!reservations.get(key).containsKey(classroomNumber)) {
            return false;
        }
        for (Reservation r : reservations.get(key).get(classroomNumber)) {
            if (r.overlaps(reservation)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Rimuove una prenotazione dalla mappa delle prenotazioni
     * @param key
     * @param classroomNumber
     * @param reservation
     * @return
     */
    public boolean removeReservation(D key, int classroomNumber, Reservation reservation) {
        if (!reservations.containsKey(key) || !reservations.get(key).containsKey(classroomNumber)) {
            return false;
        }
        
        List<Reservation> resList = reservations.get(key).get(classroomNumber);
        boolean removed = resList.remove(reservation);
        
        if (resList.isEmpty()) {
            reservations.get(key).remove(classroomNumber);
        }
        if (reservations.get(key).isEmpty()) {
            reservations.remove(key);
        }
    
        return removed;
    }
    /**
     * Aggiorna una prenotazione nella mappa delle prenotazioni con una nuova prenotazione
     * @param key
     * @param classroomNumber
     * @param oldReservation
     * @param newReservation
     * @return
     */
    public boolean updateReservation(D key, int classroomNumber, Reservation oldReservation, Reservation newReservation) {
        if (!removeReservation(key, classroomNumber, oldReservation)) {
            return false;
        }
        if (!addReservation(key, classroomNumber, newReservation)) {
            // Se la nuova prenotazione fallisce, ripristiniamo la vecchia
            addReservation(key, classroomNumber, oldReservation);
            return false;
        }
        return true;
    }

}

