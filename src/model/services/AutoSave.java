package model.services;

/**
 * Classe che rappresenta un thread per il salvataggio automatico dei dati
 */
public class AutoSave extends Thread {

    private final Writer writer;
    private final int intervalSeconds;
    private volatile boolean running;
    /**
     * Costruttore di AutoSave 
     * @param writer
     * @param intervalSeconds
     * @param running=true default
     */
    public AutoSave(Writer writer, int intervalSeconds) {
        this.writer = writer;
        this.intervalSeconds = intervalSeconds;
        this.running = true;
    }
    /**
     * Metodo che esegue il salvataggio automatico dei dati
     */
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        // Converti l'intervallo in minuti in millisecondi
        long intervalMillis = intervalSeconds * 60 * 10;
        
        try {
            while (running) {
                // Attendi l'intervallo specificato
                try {
                    Thread.sleep(intervalMillis);
                    if(writer.overWriteFile()){
                        System.out.println("Salvataggio automatico eseguito");
                    } else {
                        System.err.println("Errore nel salvataggio automatico");
                    }
                } catch (InterruptedException e) {
                    // Se interrotto, controlla se deve continuare
                    if (!running) {
                        break;
                    }
                    // Riprova il ciclo se il thread è stato interrotto senza fermarlo definitivamente
                }
            }
        } catch (Exception e) {
            System.err.println("Errore nel salvataggio automatico");
        }
    }

    /**
     * Metodo per fermare il salvataggio automatico
     */
    public void stopAutoSave() {
        running = false;  // Imposta la variabile running su false
        this.interrupt();
        try {
            this.join(); // Aspetta la chiusura del thread
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Metodo per attivare il salvataggio automatico
     */
    public void activeAutoSave() {
        running = true;  // Imposta la variabile running su true
        if (!this.isAlive()) {
            this.start(); // Avvia il thread se non è già in esecuzione
        }
        this.interrupt(); // Interrompi il thread se è in sleep
    }
}

