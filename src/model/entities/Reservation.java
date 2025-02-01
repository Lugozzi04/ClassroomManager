package model.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
/**
 * Classe che rappresenta una prenotazione
 */
public class Reservation  {
    private LocalDate date;
    private int startHour;
    private int endHour;
    private String name;
    private String reason;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Costruttore di Reservation
     * @param date
     * @param startHour
     * @param endHour
     * @param name
     * @param reason
     * @throws IllegalArgumentException se l'ora di fine è minore o uguale a quella di inizio
     */
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
    /**
     * Imposta l'ora di inizio e di fine della prenotazione
     * @param startHour
     * @param endHour
     * @return
     */
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
    /**
     * Verifica se due oggetti Reservation sono uguali, confrontando i loro attributi.
     * @param obj oggetto da confrontare
     */
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
    /**
     * Verifica se due prenotazioni si sovrappongono nello stesso giorno e in orari 
     * @param other
     * @return
     */
    public boolean overlaps(Reservation other){
        return this.date.equals(other.date) &&
                (this.startHour>=other.startHour && this.startHour<other.endHour ||
                        this.endHour>other.startHour && this.endHour<=other.endHour ||
                        this.startHour<=other.startHour && this.endHour>=other.endHour);
    }
    /**
     * Converte una stringa in una prenotazione
     * @param content
     * @return Reservation
     */
    public static Reservation stringToReservation(String content) {
        try{
        String[] p = content.split(";");
        String[] onlyDate = p[0].split("-");
        
        LocalDate date = LocalDate.of(Integer.parseInt(onlyDate[0]), Integer.parseInt(onlyDate[1]), Integer.parseInt(onlyDate[2]));
        int startHour = Integer.parseInt(p[1]);
        int endHour = Integer.parseInt(p[2]);
        String name = p[3];
        String reason = p[4];
        
        return new Reservation(date, startHour, endHour ,name, reason);
        }catch(NumberFormatException e){
            return null;
        }
    }
    /**
     * Restituisce un array di ore comprese tra l'ora di inizio e di fine della prenotazione
     * @return int[]
     */
    public int[] getHours(){
        int[] hours = new int[endHour-startHour];
        for(int i=startHour;i<endHour;i++){
            hours[i-startHour]=i;
        }
        return hours;
    }

   
    
}
