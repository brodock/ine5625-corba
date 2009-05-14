package servico;

import cliente.ClienteEventos;
import cliente.ClienteEventosHelper;
import detector.DetectorEventos;
import detector.DetectorEventosHelper;
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
import org.omg.CosNaming.NamingContextExt;
import org.omg.PortableServer.POA;
import util.Log;
import util.ObjectUtils;
import util.PingThread;
import util.Save;
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
    // Thread responsaveis pelo Timeout e Checkpoint
    private Thread pingThread;
    private TimeoutThread timeoutThread;
    // CORBA
    private POA poa;
    private NamingContextExt nc;
    // Backup
    private ServicoEventos servidorBackup;
    private boolean backup;

    public ServicoEventosImpl(boolean backup, POA poa, NamingContextExt nc) {
        this.poa = poa;
        this.nc = nc;
        this.backup = backup;


        if (this.backup) {
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

        mensagem("recebi um checkpoint");

        this.timeoutThread.Ping();
        java.lang.Object obj = ObjectUtils.deserialize(estado);
        Save save = (Save) obj;

        if (save != null && save.getHashmap() != null) {
            this.clientes_eventos = save.getHashmap();
            this.detectores = save.getDetectores();
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
        Save save = new Save();
        save.setHashmap(this.clientes_eventos);
        save.setDetectores(this.detectores);
        byte[] estado = ObjectUtils.serialize(save);
        try {
            ServicoEventos bkp = getServidorBackup();
            bkp.checkpoint(estado);
        } catch (Exception ex) {
            this.servidorBackup = null;
            System.out.println("Envio de checkout falhou...");
        }
    }

    /**
     * Transforma o serviço em execução no serviço principal
     */
    public void virarServidorPrincipal() {
        System.out.println("Tentando virar servidor PRINCIPAL...");
        this.backup = false;

        try {

            // Carrega o objeto corba
            org.omg.CORBA.Object servico_corba_obj = poa.servant_to_reference(this);

            // Altera no servidor de nomes
            nc.rebind(nc.to_name("ServicoEventos.corba"), servico_corba_obj);
            nc.unbind(nc.to_name("ServicoEventosBackup.corba"));
            System.out.println("Carregado como servidor PRINCIPAL");

            // Refaz operações de log
            refazLog();

            // Avisa detectores
            avisaDetectores();

            // Altera as threads
            this.timeoutThread = null;
            this.pingThread = new PingThread(this);
            this.pingThread.start();


        } catch (Exception ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean isBackup() {
        return backup;
    }

    private ServicoEventos getServidorBackup() {
        while (servidorBackup == null) {
            try {
                mensagem("Procurando servidor de backup...");
                LocalizaServidorBackup();
                mensagem("Servidor backup, localizado!");
            } catch (Exception ex) {
                mensagem("não encontrei...");
                try {
                    wait(1000L);
                } catch (InterruptedException ex1) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
        return servidorBackup;
    }

    /**
     * Procura o servidor de backup e registra ele para utilização posterior
     *
     * @throws org.omg.CosNaming.NamingContextPackage.InvalidName
     * @throws org.omg.CosNaming.NamingContextPackage.CannotProceed
     * @throws org.omg.CosNaming.NamingContextPackage.NotFound
     */
    private void LocalizaServidorBackup() throws Exception {

        org.omg.CORBA.Object server = nc.resolve(nc.to_name("ServicoEventosBackup.corba"));
        this.servidorBackup = ServicoEventosHelper.narrow(server);
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
            this.getServidorBackup().log(copiaRequisicao, evento, ref);
        } catch (Exception ex) {
            mensagem("Ocorreu uma falha ao tentar enviar o log.");
        }
    }

    private void refazLog() {
        if (this.lista_log.isEmpty()) {
            mensagem("Log esta limpo...");
        } else {
            mensagem("Refazendo operações do log");
            for (Log log : lista_log) {
                if (log.getCopiaRequisicao().equals("CadastrarEvento")) {
                    this.CadastrarEvento(log.getEvento());
                } else if (log.getCopiaRequisicao().equals("MeRegistre")) {
                    this.MeRegistre(log.getRef(), log.getEvento());
                }
            }
        }
    }

    private void avisaDetectores() {

        if (detectores.isEmpty()) {
            mensagem("Nenhum detector está conectado...");
        } else {
            mensagem("Avisando detectores...");

            try {
                // Carrega o objeto corba
                org.omg.CORBA.Object server = poa.servant_to_reference(this);

                for (Object d : detectores) {
                    DetectorEventos detector = DetectorEventosHelper.narrow(d);
                    detector.TrocaServidor(server);
                }

            } catch (Exception e) {
                mensagem("Avisar os detectores falhou...");
            }
        }
    }
}
