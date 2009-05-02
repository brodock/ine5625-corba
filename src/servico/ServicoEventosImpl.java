package servico;

import cliente.ClienteEventos;
import cliente.ClienteEventosHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omg.CORBA.Object;
import org.omg.CORBA.StringHolder;

/**
 *
 * @author brodock
 */
public class ServicoEventosImpl extends ServicoEventosPOA {

    private HashMap<String, ArrayList<Object>> clientes_eventos = new HashMap<String, ArrayList<Object>>();
    private ArrayList<Object> detectores = new ArrayList<Object>();
    private String evt;
    private int count_evt = 0;
    private Servidor servidor;

    public ServicoEventosImpl(Servidor servidor) {
        this.servidor = servidor;
    }

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

            if (lista.contains(ref)) {
                return false;
            } else {
                mensagem("Registrando um cliente para evento: " + evento);
                return lista.add(ref);
            }

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
    public void ObterListaEventos(servico.listaEventosHolder lista) {
        String[] lista_eventos = new String[clientes_eventos.size()];

        // Recuperar Iterator da chave do hashmap
        Set<Entry<String, ArrayList<Object>>> set = clientes_eventos.entrySet();
        Iterator iter = set.iterator();

        // Pegar cada item da chave do hashmap e jogar para o array de strings
        int i = 0;
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            lista_eventos[i] = (String) entry.getKey();
            i++;
        }

        // Repassar a lista de strings de eventos para listaEventosHolder
        lista.value = lista_eventos;

        mensagem("Alguem solicitou a lista de eventos.");

        for (String e : lista_eventos) {
            mensagem(e);
        }
    }

    /**
     * Dispara um novo evento do tipo informado.
     *
     * @param evento
     * @return
     */
    public boolean NovoEvento(String evento) {

        this.evt = evento;
        this.count_evt++;

        ArrayList<Object> clientes = this.clientes_eventos.get(evento);
        if (clientes != null) {
            for (Object cli : clientes) {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServicoEventosImpl.class.getName()).log(Level.SEVERE, null, ex);
                    mensagem("Erro ao disparar evento: " + evento);
                    return false;
                }
                ClienteEventos cliente = ClienteEventosHelper.narrow(cli);
                cliente.NovoAlerta(evento);
            }
            mensagem("Evento: " + evento + " foi disparado para os clientes inscritos.");
            return true;
        } else {
            mensagem("Tentou disparar evento: " + evento + " mas o mesmo nao existe!");
            return false;
        }

    }

    /**
     * Cria uma nova categoria de eventos.
     *
     * @param evento
     * @return
     */
    public boolean CadastrarEvento(String evento) {

        mensagem("Tentando cadastrar um novo evento: " + evento);
        if (!this.clientes_eventos.containsKey(evento)) {
            this.clientes_eventos.put(evento, new ArrayList(5));
            mensagem("Evento \"" + evento + "\" cadastrado!");
            return true;
        }
        mensagem("Impossivel cadastrar evento: " + evento + " pois o mesmo ja deve estar cadastrado!");
        return false;
    }

    /**
     * Recupera um evento qualquer do Servidor.
     *
     * @param evento
     * @return
     */
    public boolean obterEventoQualquer(StringHolder evento) {
        int sequencia = this.count_evt;
        int sequencia_nova = sequencia;

        while (sequencia == sequencia_nova) {
            try {
                Thread.sleep(150L);
                sequencia_nova = this.count_evt;
            } catch (InterruptedException ex) {
                Logger.getLogger(ServicoEventosImpl.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        evento.value = new String(this.evt);
        return true;
    }

    private void mensagem(String texto) {
        System.out.println(texto);
    }

    public boolean checkpoint(byte[] estado) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean log(String copiaRequisicao, String evento, Object ref) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean RegistraDetector(Object ref) {
        this.detectores.add(ref);
        return true;
    }
}
