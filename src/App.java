import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.entities.*;
import model.services.Cache;
import model.services.ChacheLoader;
import model.services.WriterCache;
import model.struct.ManagerClassReservations;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");


    }


    public List<Classroom> getListClassroom(){
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

    public void initReservation(){
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
