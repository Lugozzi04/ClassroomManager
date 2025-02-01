package model.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/**
 * Classe che rappresenta un loader per la cache
 */
public final class CacheLoader {
    private final String fileName;
    /**
     * Costruttore di CacheLoader
     * @param fileName nome del file
     */
    public CacheLoader(String fileName) {
        this.fileName = fileName;
    }
    /**
     * Metodo per caricare la cache da file 
     * @return 
     */
    public Cache loadCache() {
        Cache cache = new Cache();
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File created: " + fileName);
                } else {
                    System.err.println("Failed to create file: " + fileName);
                    return null;
                }
            } catch (IOException e) {
                System.err.println("Errore nel LoadCache");
                return null;
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                cache.addLine(line);
            }
        } catch (IOException e) {
            System.err.println("Errore nel LoadCache");
            return null;
        }
        return cache;
    }
    /**
     * Metodo per caricare la cache da file 
     * @param cache
     * @return 
     */
    public Cache loadCache(Cache cache) {
        cache = new Cache();
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File created: " + fileName);
                } else {
                    System.err.println("Failed to create file: " + fileName);
                    return null;
                }
            } catch (IOException e) {
                System.err.println("Errore nel LoadCache");
                return null;
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                cache.addLine(line);
            }
        } catch (IOException e) {
            System.err.println("Errore nel LoadCache");
            return null;
        }
        return cache;
    }

}
