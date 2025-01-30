package model.struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.entities.*;


public class ManagerClassReservations <D,C extends Classroom> {

    private final Map<Integer, C> classrooms;
    private final Map<D, Map<Integer, List<Reservation>>> reservations;

    public ManagerClassReservations(List<C> classrooms) {
        this.reservations = new HashMap<>();
        this.classrooms = initClassrooms(classrooms);
    }
    //TODO: Serve?
    public List<C> getClassrooms(D key) {
        List<C> list = new ArrayList<>();
        if (reservations.containsKey(key)) {
            for (Map.Entry<Integer, List<Reservation>> entry : reservations.get(key).entrySet()) {
                list.add(classrooms.get(entry.getKey()));
            }
        }
        return list;
    }

    private Map<Integer, C> initClassrooms(List<C> classrooms) {
        Map<Integer, C> map = new HashMap<>();
        for (C c : classrooms) {
            map.put(c.getNumber(), c);
        }
        return map;
    }

    public int getClassroomNumber(C classroom){
        for (Map.Entry<Integer, C> entry : classrooms.entrySet()) {
            if (entry.getValue().equals(classroom)) {
                // Trova la Classroom corrispondente 
                return entry.getKey(); // la chiave associata
            }
        }
        return -1; // Non trovata
    }

    private boolean isClassroom(int classroomNumber) {
        return classrooms.containsKey(classroomNumber);
    }

    public Classroom getClassroom(int classroomNumber) {
        return classrooms.get(classroomNumber);
    }
    
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


    public boolean addReservation(D key, int classroomNumber, Reservation reservation) {
        if (!isClassroom(classroomNumber) || isOverlapping(key, classroomNumber, reservation)) {
            return false;
        }

        reservations.putIfAbsent(key, new HashMap<>());
        reservations.get(key).putIfAbsent(classroomNumber, new ArrayList<>());

        return reservations.get(key).get(classroomNumber).add(reservation);
    }


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

