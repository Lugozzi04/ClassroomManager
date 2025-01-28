package model.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class ChacheLoader<C extends Cache> {
    private final String fileName;
    private final C cache;

    public ChacheLoader(String fileName, C cache) {
        this.fileName = fileName;
        this.cache = cache;
        loadToCache();
    }

    public void loadToCache() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                cache.addLine(line);
            }
        } catch (IOException e) {
            
        }
        
    }
}
