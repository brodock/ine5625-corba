package servico;


import cliente.ClienteEventos;
import java.util.ArrayList;
import java.util.HashMap;
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

    private HashMap<String, ArrayList> clientes_eventos = new HashMap<String, ArrayList>();

    /**
     * Registra um objeto cliente para receber Eventos do tipo
     * especificado.
     *
     * @param ref
     * @param evento
     * @return
     */
    public boolean MeRegistre(Object ref, String evento) {

        if (this.clientes_eventos.containsKey(evento)) {

            ArrayList lista = (ArrayList) this.clientes_eventos.get(evento);
            
            if (lista.contains(ref))
                return false;
            else
                return lista.add(ref);

        }
        return false;
        
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
        if (!this.clientes_eventos.containsKey(evento)) {
            this.clientes_eventos.put(evento, new ArrayList(5));
            return true;
        }
        return false;
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
