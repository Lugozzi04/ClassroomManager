package model.services;
/**
 * Classe che rappresenta un'area di memoria temporanea
 */
public class Cache {

    private final StringBuilder cache;
    /**
     * Costruttore di Cache
     * Inizializza la cache come una stringa vuota
     */
    public Cache() {
        this.cache = new StringBuilder();
    }
    /**
     * Costruttore di Cache con parametro cache
     * @param cache
     */
    public Cache(String cache) {
        this.cache = new StringBuilder(cache);
    }
    /**
     * Metodo per ottenere il contenuto della cache
     * @return
     */
    public String getCache() {
        return cache.toString();
    }

    /**
     * Metodo per aggiungere una riga alla cache
     * @param line
     * @return 
     */
    public boolean addLine(String line) {
        cache.append(line).append("\n");
        return true;
    }
    /**
     * Metodo per ottenere una riga dalla cache
     * @param n
     * @return
     */
    public String getLine(int n) {
        String[] righe = cache.toString().split("\n");
        if (n < 0 || n >= righe.length) {
            return null;
        }
        return righe[n];
    }
    /**
     * Metodo per rimuovere una riga dalla cache
     * @param n numero di riga
     * @return
     */
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
    /**
     * Metodo per ottenere il numero di righe della cache
     * @return
     */
    public int getSize() {
        //se il file è vuoto ritorna 0 altrimenti ritorna il numero di righe
        if (cache.length() == 0) {
            return 0;
        }
        return cache.toString().split("\n").length;
    }
    /**
     * Metodo per ottenere l'indice di una riga nella cache
     * @param searchString
     * @return
     */
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
