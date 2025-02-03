package model.entities;
/**
 * Classe che rappresenta un'aula di tipo laboratorio
 */
public class ClassroomLab extends Classroom {
    /**
     * Costruttore di ClassroomLab
     * @param number
     * @param capacity
     * @param info
     */
    public ClassroomLab(int number, int capacity, String info) {
        super(number, capacity, info, "Laboratorio");
    }

    @Override
    public boolean isBookable(int hours) {
        return hours == 2 || hours == 4;
    }

    @Override
    public String ruleForBooking() {
        return "prenotabile per 2 o 4 ore";
    }
    
}
