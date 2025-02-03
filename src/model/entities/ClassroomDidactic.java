package model.entities;
/** 
 * Classe che rappresenta un'aula didattica
 */
public class ClassroomDidactic extends Classroom {
    /**
     * Costruttore di ClassroomDidactic
     * @param numero
     * @param capienza
     * @param info
     */
    public ClassroomDidactic(int numero, int capienza,String info) {
        super(numero, capienza,info, "Didattica");
    }

    @Override
    public boolean isBookable(int durata) {
        return durata >= 1 && durata <= 8;
    }

    @Override
    public String ruleForBooking() {
        return "prenotazioni comprese tra 1 e 8 ore";
    }
}
