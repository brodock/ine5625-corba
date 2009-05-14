package util;

import java.util.logging.Level;
import java.util.logging.Logger;
import servico.ServicoEventosImpl;

/**
 *
 * @author brodock
 */
public class PingThread extends Thread {

    private ServicoEventosImpl servico;

    public PingThread(ServicoEventosImpl servico) {
        this.servico = servico;
        System.out.println("Inicializando ping thread!");
    }

    @Override
    public void run() {
        synchronized (this) {
            while (!this.servico.isBackup()) {
                this.servico.enviarCheckpoint();
                try {
                    wait(5000L);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServicoEventosImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
