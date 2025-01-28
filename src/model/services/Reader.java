package model.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    private final String fileName;

    public Reader(String fileName) {
        this.fileName = fileName;
    }

    public Cache loadToCache() {
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
}
