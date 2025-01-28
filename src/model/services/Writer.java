package model.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private final String fileName;
    private final Cache cache;

    public Writer(String fileName,Cache cache) {
        this.fileName = fileName;
        this.cache = cache;
    }

    public boolean saveToFile() {
        return overWriteFile();
    }

    public void addLine(String line) {
        cache.addLine(line);
    }

    public boolean removeLine(int index) {
        return cache.removeLine(index);
    }

    private boolean overWriteFile(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(cache.getCache());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
