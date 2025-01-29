package model.services;

public class AutoSave extends Thread {

    private final WriterCache writer;
    private final int intervalMinutes;
    private volatile boolean running;

    public AutoSave(WriterCache writer, int intervalMinutes) {
        this.writer = writer;
        this.intervalMinutes = intervalMinutes;
        this.running = true;
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        // Converti l'intervallo in minuti in millisecondi
        long intervalMillis = intervalMinutes * 60 * 1000;
        
        try {
            while (running) {
                // Attendi l'intervallo specificato
                try {
                    Thread.sleep(intervalMillis);
                    if(writer.saveToFile()){
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

    // Metodo per fermare il ciclo
    public void stopAutoSave() {
        running = false;  // Imposta la variabile running su false
        this.interrupt();
        try {
            this.join(); // Aspetta la chiusura del thread
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Metodo per riattivare il ciclo
    public void activeAutoSave() {
        running = true;  // Imposta la variabile running su true
        if (!this.isAlive()) {
            this.start(); // Avvia il thread se non è già in esecuzione
        }
        this.interrupt(); // Interrompi il thread se è in sleep
    }
}

