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

    private Map<Integer, C> initClassrooms(List<C> classrooms) {
        Map<Integer, C> map = new HashMap<>();
        for (C c : classrooms) {
            map.put(c.getNumber(), c);
        }
        return map;
    }

    /*private void addClassroom(C classroom) {
        classrooms.put(classroom.getNumber(), classroom);
    }*/


    public int getClassroomNumber(C Classroom){
        for (Map.Entry<Integer, C> entry : classrooms.entrySet()) {
            if (entry.getValue().equals(Classroom)) {
                // Trova la Classroom corrispondente 
                return entry.getKey(); // la chiave associata
            }
        }
        return -1; // Non trovata
    }

    private boolean isClassroom(int classroomNumber) {
        return classrooms.containsKey(classroomNumber);
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
        if (!reservations.containsKey(key) || !reservations.get(key).containsKey(classroomNumber)) {
            return false;
        }
    
        List<Reservation> resList = reservations.get(key).get(classroomNumber);
        if (!resList.remove(oldReservation)) {
            return false;
        }
        
        if (resList.isEmpty()) {
            reservations.get(key).remove(classroomNumber);
        }
    
        return addReservation(key, classroomNumber, newReservation);
    }

}

