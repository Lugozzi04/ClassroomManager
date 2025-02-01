package model.entities;

import java.util.Objects;
/**
 * Classe astratta che rappresenta un'aula
 */
public abstract class Classroom {
    private int number;
    private int capacity;
    private final String type;
    private String info;

    /**
     * Costruttore di Classroom
     * @param number numero dell'aula
     * @param capacity capienza dell'aula
     * @param info informazioni sull'aula
     * @param type tipo di aula
     * @throws IllegalArgumentException se la capienza o il numero sono negativi.
     */
    public Classroom(int number, int capacity, String info,String type) {
        if(capacity<0){
            throw new IllegalArgumentException("La capienza non può essere negativa");
        }
        if(number<0){
            throw new IllegalArgumentException("Il numero non può essere negativo");
        }
        this.number = number;
        this.capacity = capacity;
        this.type = type;
        this.info = info;
    }

    /**
     * Verifica se l'aula è prenotabile per un certo numero di ore.
     *
     * @param hours Il numero di ore per cui si vuole prenotare l'aula.
     * @return true se l'aula è prenotabile, false altrimenti.
     */
    public abstract boolean isBookable(int hours);

    /**
     * Restituisce il tipo di aula.
     * @return
     */
    public String getType(){
        return type;
    }

    /**
     * Restituisce il numero dell'aula.
     */
    public int getNumber(){
        return number;
    }
    /**
     * Imposta il numero dell'aula.
     */
    public void setNumber(int number) {
        this.number = number;
    }
    /**
     * Restituisce la capienza dell'aula.
     * @return
     */
    public int getCapacity(){
        return capacity;
    }
    /**
     * Imposta la capienza dell'aula.
     * @param capacity
     */
    public void setCapacity(int capacity){
        this.capacity=capacity;
    }
    /**
     * Restituisce le informazioni sull'aula.
     * @return
     */
    public String getInfo(){
        return info;
    }
    /**
     * Imposta le informazioni sull'aula.
     * @param info
     */
    public void setInfo(String info){
        this.info=info;
    }
    /**
     * Verifica se due oggetti Classroom sono uguali, confrontando i loro attributi.
     * @param obj oggetto da confrontare
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Classroom that = (Classroom) obj;
        return number == that.number &&
               capacity == that.capacity &&
               type.equals(that.type) &&
               Objects.equals(info, that.info);  
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, capacity, info, type);
    }

    @Override
    public String toString(){
        return "Aula:"+number+" "+type;
    }
    /**
     * Metodo che converte una stringa in un oggetto Classroom.
     * @param content
     * @return Classroom
     */
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
