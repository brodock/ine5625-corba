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
import util.Log;
import util.ObjectUtils;
import util.PingThread;
import util.TimeoutThread;

/**
 *
 * @author brodock
 */
public class ServicoEventosImpl extends ServicoEventosPOA {

    // Estrutura de dados
    private HashMap<String, ArrayList<Object>> clientes_eventos = new HashMap<String, ArrayList<Object>>();
    private ArrayList<Object> detectores = new ArrayList<Object>();
    private ArrayList<Log> lista_log = new ArrayList<Log>();
    // Lidar com caso de disparar evento qualquer
    private String evt;
    private int count_evt = 0;
    // Instancia da classe Servidor (responsável pela inicialização do corba e localizar servidor de backup)
    public Servidor servidor;
    // Thread responsaveis pelo Timeout e Checkpoint
    private Thread pingThread;
    private TimeoutThread timeoutThread;

    public ServicoEventosImpl(Servidor servidor) {
        this.servidor = servidor;

        if (this.servidor.isBackup()) {
            this.timeoutThread = new TimeoutThread(this);
            this.timeoutThread.start();
        } else {
            this.pingThread = new PingThread(this);
            this.pingThread.start();
        }
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
                mensagem("Cliente já está registrado para evento: " + evento);
                return false;
            } else {
                mensagem("Registrando um cliente para evento: " + evento);
                this.addToLog("MeRegistre", evento, ref);
                return lista.add(ref);
            }

        }
        mensagem("Cliente tentou se registrar para evento inexistente: " + evento);
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
            this.addToLog("CadastrarEvento", evento, null);
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

    /**
     * Quando receber um estado, substitui a lista com eventos e clientes existentes
     * pela lista fornecida no estado. O estado é um array de bytes, e precisa ser deserializado.
     * @param estado
     * @return
     */
    public boolean checkpoint(byte[] estado) {

        this.timeoutThread.Ping();
        java.lang.Object obj = ObjectUtils.deserialize(estado);
        HashMap<String, ArrayList<Object>> hashmap = (HashMap<String, ArrayList<Object>>) obj;

        if (hashmap != null) {
            this.clientes_eventos = hashmap;
            this.lista_log = new ArrayList<Log>();
            return true;
        } else {
            mensagem("Falha no checkpoint recebido...");
            return false;
        }
    }

    /**
     * Adiciona os registros referentes a uma requisição na lista de logs
     * @param copiaRequisicao
     * @param evento
     * @param ref
     * @return true caso positivo ou false caso tenha ocorrido algum erro
     */
    public boolean log(String copiaRequisicao, String evento, Object ref) {
        System.out.println("Log recebido: " + copiaRequisicao + " " + evento + " ");
        Log log = new Log(copiaRequisicao, evento, ref);
        return this.lista_log.add(log);
    }

    /**
     * Adiciona um detector a lista de detectores registrados
     * @param ref Referência do Detector
     * @return true se conseguir ou false se falhar
     */
    public boolean RegistraDetector(Object ref) {
        return this.detectores.add(ref);
    }

    /**
     * Envia o checkpoint para o servidor de backup
     */
    public void enviarCheckpoint() {
        byte[] estado = ObjectUtils.serialize(this.clientes_eventos);
        this.servidor.getServidorBackup().checkpoint(estado);
    }

    /**
     * Utilizado pelo servidor principal, para enviar o log para o servidor backup.
     *
     * @param copiaRequisicao
     * @param evento
     * @param ref
     */
    private void addToLog(String copiaRequisicao, String evento, Object ref) {

        try {
            this.servidor.getServidorBackup().log(copiaRequisicao, evento, ref);
        } catch (Exception ex) {
            System.out.println("Ocorreu uma falha ao tentar enviar o log.");
        }
    }
}
