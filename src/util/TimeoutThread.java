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
public class TimeoutThread extends Thread {

    ServicoEventosImpl servico;
    int count = 0;
    private static final int MAX_TIMEOUT = 20; // 20 segundos
    private static final long TIMEOUT_UNIT = 1000L;

    public TimeoutThread(ServicoEventosImpl servico) {
        this.servico = servico;
    }

    /**
     * Ao receber um ping, zera o contador de tempo
     */
    public void Ping() {
        this.count = 0;
    }

    @Override
    public void run() {
        
        synchronized(this) {
        while (count <= MAX_TIMEOUT) {
            try {
                this.count++;
                wait(TIMEOUT_UNIT);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServicoEventosImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.servico.servidor.virarServidorPrincipal();
        }
    }
}
