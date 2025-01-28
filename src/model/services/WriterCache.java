package model.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriterCache <C extends Cache> {
    private final String fileName;
    private final C cache;

    public WriterCache(String fileName,C cache) {
        this.fileName = fileName;
        this.cache = cache;
    }

    public boolean saveToFile() {
        return overWriteFile();
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
