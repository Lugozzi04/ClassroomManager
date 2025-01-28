package model.entities;

import java.util.Objects;

public abstract class Classroom {
    private int number;
    private int capacity;
    private final String type;
    private String info;

    public Classroom(int number, int capacity, String info,String type) {
        if(capacity<0&&number<0){
            throw new IllegalArgumentException("La capienza non può essere negativa");
        }
        this.number = number;
        this.capacity = capacity;
        this.type = type;
        this.info = info;
    }
    
    public abstract boolean isBookable(int hours);

    //all get and set for all attributes
    public String getType(){
        return type;
    }

    //non esiste il set tipo
    public int getNumber(){
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public int getCapacity(){
        return capacity;
    }
    public void setCapacity(int capacity){
        this.capacity=capacity;
    }
    public String getInfo(){
        return info;
    }
    public void setInfo(String info){
        this.info=info;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Classroom that = (Classroom) obj;
        return number == that.number &&
               capacity == that.capacity &&
               type.equals(that.type) &&
               Objects.equals(info, that.info);  // Usa Objects.equals per evitare problemi con null
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, capacity, info, type);
    }

    @Override
    public String toString(){
        return "Aula:"+number+" "+type;
    }

    public Classroom getClassroom(){
        return this;
    }

    public static Classroom stringToClassroom(String content){
        String[] values = content.split("\\;");
        if(values.length!=4){
            return null;
        }
        if(values[3].equals("Laboratorio")){
            return new ClassroomLab(Integer.parseInt(values[0]),Integer.parseInt(values[1]),values[2]);
        }else if(values[3].equals("Didattica")){
            return new ClassroomDidactic(Integer.parseInt(values[0]),Integer.parseInt(values[1]),values[2]);
        }
        return null;
    }
}
