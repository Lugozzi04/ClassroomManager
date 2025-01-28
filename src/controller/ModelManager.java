package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.entities.*;
import model.services.Cache;
import model.services.ChacheLoader;
import model.services.WriterCache;
import model.struct.*;

public class ModelManager<D,C extends Classroom> {
    
    private ManagerClassReservations<LocalDate,Classroom> manager;
    private Cache cacheReservation;
    private WriterCache<Cache> writerReservation;
    



    public ModelManager(String fileName) {
        this.manager = new ManagerClassReservations<>(getListClassroom());
        this.cacheReservation = new Cache();
        ChacheLoader<Cache> loader = new ChacheLoader<>(fileName, cacheReservation);
        loader.loadToCache();
        writerReservation = new WriterCache<>(fileName, cacheReservation);
    }
    
    public boolean addReservation(Reservation r) {
        // TODO implement here
        return false;
    }

    public boolean removeReservation(Reservation r) {
        // TODO implement here
        return false;
    }

    public boolean updateReservation(Reservation r) {
        // TODO implement here
        return false;
    }

    public boolean save(){
        // TODO implement here
        return false;
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
        Cache cacheClass = new Cache();
        ChacheLoader<Cache> loader = new ChacheLoader<>("file.txt", cacheClass);
        loader.loadToCache();
        WriterCache<Cache> writer = new WriterCache<>("file.txt", cacheClass);

        for (int i = 0; i < cacheClass.getSize(); i++) {
            String line = cacheClass.getLine(i);      
            classes.add(Classroom.stringToClassroom(line));
        }
        return classes;
    }

    private void initReservation(){
        Cache cacheRes = new Cache();
        ChacheLoader<Cache> loader = new ChacheLoader<>("file.txt", cacheRes);
        loader.loadToCache();
        WriterCache<Cache> writer = new WriterCache<>("file.txt", cacheRes);
        ManagerClassReservations<LocalDate, Classroom> manager = new ManagerClassReservations<>(getListClassroom());

        for (int i = 0; i < cacheRes.getSize(); i++) {
            String line = cacheRes.getLine(i);      
            String[] parts = line.split("\\{");
            Reservation r = Reservation.stringToReservation(parts[1]);
            manager.addReservation(r.getDate(),Integer.parseInt(parts[0]),r);
        }
        
    }

}
