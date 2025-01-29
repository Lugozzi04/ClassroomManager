package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.entities.*;
import model.services.*;
import model.struct.*;

public class ModelManager {
    
    private final ManagerClassReservations<LocalDate,Classroom> manager;
    private final Cache cacheReservation;
    private final WriterCache writerReservation;
    

    public ModelManager(String fileName) {
        this.manager = new ManagerClassReservations<>(getListClassroom());
        ChacheLoader loader = new ChacheLoader(fileName);
        cacheReservation=loader.loadCache();
        writerReservation = new WriterCache(fileName, cacheReservation);
        initReservation();
    }
    
    public boolean addReservation(Reservation r, int classroomNumber) {
        return manager.addReservation(r.getDate(), classroomNumber, r)&&
                cacheReservation.addLine(classroomNumber + "{" + r.toString());
    }

    public boolean removeReservation(Reservation r, int classroomNumber) { 
        return manager.removeReservation(r.getDate(), classroomNumber, r)&&
                cacheReservation.removeLine(cacheReservation.getCache().indexOf(classroomNumber + "{" + r.toString()));
    }

    public boolean updateReservation(Reservation oldR,Reservation newR,int classroomNumber) {
        return manager.updateReservation(oldR.getDate(), classroomNumber, oldR, newR)&&
                cacheReservation.removeLine(cacheReservation.getCache().indexOf(classroomNumber + "{" + oldR.toString()))&&
                cacheReservation.addLine(classroomNumber + "{" + newR.toString());
    }

    public boolean save(){
        return writerReservation.saveToFile();
    }

    public void autoSave(int intervalMinutes){
        AutoSave threadSave=new AutoSave(writerReservation, intervalMinutes);
        threadSave.start();
    }

    public Reservation getReservation(LocalDate date, int id) {
        // TODO implement here
        return null;
    }

    public Classroom getClassroom(int id) {
        // TODO implement here
        return null;
    }

    private List<Classroom> getListClassroom(){
        List<Classroom> classes = new ArrayList<>();
        ChacheLoader loader = new ChacheLoader("Classrooms.txt");
        Cache cacheClass = loader.loadCache();

        for (int i = 0; i < cacheClass.getSize(); i++) {
            String line = cacheClass.getLine(i);      
            classes.add(Classroom.stringToClassroom(line));
        }
        return classes;
    }

    private void initReservation(){
        for (int i = 0; i < cacheReservation.getSize(); i++) {
            String line = cacheReservation.getLine(i);      
            String[] parts = line.split("\\{");
            Reservation r = Reservation.stringToReservation(parts[1]);
            manager.addReservation(r.getDate(),Integer.parseInt(parts[0]),r);
        }
        
    }

}
