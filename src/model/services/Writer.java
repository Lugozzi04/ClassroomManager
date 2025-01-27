package model.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private final String fileName;
    private Cache c;

    public Writer(String fileName, Cache c) {
        this.fileName = fileName;
        this.c = c;
    }

    public void save(){
        overWriteFile(c.getCache());
    }

    public void removeLine(int index){
        c.removeLine(index);
    }
    public void addLine(String line){
        c.addLine(line);
    }

    private boolean  overWriteFile(String content){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(content);
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    //get set
    public Cache getCache(){
        return c;
    }
    public void setCache(Cache c){
        this.c=c;
    }


}
