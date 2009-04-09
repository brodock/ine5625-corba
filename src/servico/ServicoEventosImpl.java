package servico;


import servico.listaEventosHolder;
import org.omg.CORBA.Object;
import org.omg.CORBA.StringHolder;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brodock
 */
public class ServicoEventosImpl extends ServicoEventosPOA {

    /**
     * Registra um objeto cliente para receber Eventos do tipo
     * especificado.
     *
     * @param ref
     * @param evento
     * @return
     */
    public boolean MeRegistre(Object ref, String evento) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Preenche uma lista com os eventos existentes no
     * Servidor, para que o cliente escolha para quais
     * eventos quer estar registrado.
     *
     * @param lista
     */
    public void ObterListaEventos(listaEventosHolder lista) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Dispara um novo evento do tipo informado.
     *
     * @param evento
     * @return
     */
    public boolean NovoEvento(String evento) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Cria uma nova categoria de eventos.
     *
     * @param evento
     * @return
     */
    public boolean CadastrarEvento(String evento) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Recupera um evento qualquer do Servidor.
     *
     * @param evento
     * @return
     */
    public boolean obterEventoQualquer(StringHolder evento) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
