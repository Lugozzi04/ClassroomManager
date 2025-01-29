package model.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class ChacheLoader {
    private final String fileName;

    public ChacheLoader(String fileName) {
        this.fileName = fileName;
    }

    public Cache loadCache() {
        Cache cache = new Cache();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                cache.addLine(line);
            }
        } catch (IOException e) {
            return null;
        }
        return cache;
    }
    
    public Cache loadCache(Cache cache) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                cache.addLine(line);
            }
        } catch (IOException e) {
            return null;
        }
        return cache;
    }
}
