package model.entities;

public class ClassroomDidactic extends Classroom {

    public ClassroomDidactic(int numero, int capienza,String info) {
        super(numero, capienza,info, "Didattica");
    }

    @Override
    public boolean isBookable(int durata) {
        return durata >= 1 && durata <= 8;
    }
}
