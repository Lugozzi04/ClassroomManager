package model.services;

public class Cache {

    private final StringBuilder cache;

    public Cache() {
        this.cache = new StringBuilder();
    }

    public Cache(String cache) {
        this.cache = new StringBuilder(cache);
    }

    public String getCache() {
        return cache.toString();
    }

    public boolean addLine(String line) {
        cache.append(line).append("\n");
        return true;
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
        cache.setLength(0); // Resetta il contenuto della cache

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

    public int getLineIndex(String searchString) {
        String[] righe = cache.toString().split("\n");
        for (int i = 0; i < righe.length; i++) {
            if (righe[i].equals(searchString)) {
                return i;
            }
        }
        return -1; 
    }
}
