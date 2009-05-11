/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
    }

    @Override
    public void run() {
        while (this.servico.servidor.isBackup()) {
            this.servico.enviarCheckpoint();
            try {
                wait(3000L);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServicoEventosImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
