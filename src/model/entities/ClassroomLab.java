package model.entities;

public class ClassroomLab extends Classroom {

    public ClassroomLab(int number, int capacity, String info) {
        super(number, capacity, info, "Laboratorio");
    }

    @Override
    public boolean isBookable(int hours) {
        return hours == 2 || hours == 4;
    }
    
}
