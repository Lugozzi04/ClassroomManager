package model.services;

import java.io.*;

public final class Cache {
    private final String fileName;
    private final StringBuilder cache;

    public Cache(String fileName) {
        this.fileName = fileName;
        this.cache = new StringBuilder();
    }

    public boolean loadFromFile() {
        cache.setLength(0); // Svuota la cache prima di ricaricare

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                cache.append(line).append("\n");
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public String getCache() {
        return cache.toString();
    }

    public void addLine(String line) {
        cache.append(line).append("\n");
    }

    public String getLine(int n) {
        String[] righe = cache.toString().split("\n");
        if (n < 0 || n >= righe.length) {
            return null;
        }
        return righe[n];
    }

    public boolean removeLine(int n) {
        String[] righe = cache.toString().split("\n");
        if (n < 0 || n >= righe.length) {
            return false;
        }
        cache.setLength(0);

        for (int i = 0; i < righe.length; i++) {
            if (i != n) {
                cache.append(righe[i]).append("\n");
            }
        }
        return true;
    }

    public int getSize() {
        return cache.toString().split("\n").length;
    }
}
