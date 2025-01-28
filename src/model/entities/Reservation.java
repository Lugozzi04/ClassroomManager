package model.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Reservation  {
    private LocalDate date;
    private int startHour;
    private int endHour;
    private String name;
    private String reason;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    
    public Reservation(LocalDate date, int startHour, int endHour, String name, String reason) {
        if(endHour<=startHour ){
            throw new IllegalArgumentException("End hour must be greater than start hour");
        }
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.name = name;
        this.reason = reason;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public String getName() {
        return name;
    }

    public String getReason() {
        return reason;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean setHours(int startHour, int endHour) {
        if(endHour<=startHour ){
            return false;
        }
        this.startHour = startHour;
        this.endHour = endHour;
        return true;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return date.format(DATE_FORMATTER) + ";" + startHour + ";" + endHour + ";" + name + ";" + reason;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reservation that = (Reservation) obj;
        return date.equals(that.date) &&
                startHour==that.startHour &&
                endHour==that.endHour &&
                name.equals(that.name) &&
                reason.equals(that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date,startHour,endHour,name,reason);
    }

    public boolean overlaps(Reservation other){
        return this.date.equals(other.date) &&
                (this.startHour>=other.startHour && this.startHour<other.endHour ||
                        this.endHour>other.startHour && this.endHour<=other.endHour ||
                        this.startHour<=other.startHour && this.endHour>=other.endHour);
    }

    public static Reservation stringToReservation(String content) {
        String[] p = content.split(",");
        String[] onlyDate = p[0].split("-");
        
        LocalDate date = LocalDate.of(Integer.parseInt(onlyDate[0]), Integer.parseInt(onlyDate[1]), Integer.parseInt(onlyDate[2]));
        int startHour = Integer.parseInt(p[1]);
        int endHour = Integer.parseInt(p[2]);
        String name = p[3];
        String reason = p[4];
        
        return new Reservation(date, startHour, endHour ,name, reason);
    }

   
    
}
