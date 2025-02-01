package model.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Classe che rappresenta un writer per la scrittura su file
 */
public class Writer {
    private final String fileName;
    private final Cache cache;
    /**
     * Costruttore di Writer con parametro fileName e cache
     * @param fileName
     * @param cache
     */
    public Writer(String fileName,Cache cache) {
        this.fileName = fileName;
        this.cache = cache;
    }
    /**
     * Costruttore di Writer con parametro fileName 
     * @param fileName
     */
    public Writer(String fileName){
        this.fileName = fileName;
        this.cache = null;
    }

    /**
     * Metodo per sovrascrivere il file con il contenuto della cache
     * @return
     */
    public boolean overWriteFile(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(cache.getCache());
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    /**
     * Metodo per cancellare il file
     * @return
     */
    public boolean deleteFile() {
        File file = new File(fileName);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
    /**
     * Metodo per sovrascrivere il file con il contenuto passato come parametro
     * @param context contenuto da scrivere
     * @return 
     */
    public boolean overWriteFile(String context){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(context);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
