package model.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private final String fileName;
    private final Cache cache;

    public Writer(String fileName,Cache cache) {
        this.fileName = fileName;
        this.cache = cache;
    }
    public Writer(String fileName){
        this.fileName = fileName;
        this.cache = null;
    }


    public boolean overWriteFile(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(cache.getCache());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean deleteFile() {
        File file = new File(fileName);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    public boolean overWriteFile(String context){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(context);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
