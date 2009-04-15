package cliente;

import detector.Detector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import servico.ServicoEventos;
import servico.ServicoEventosHelper;
import servico.listaEventosHolder;

/**
 *
 * @author brodock
 */
public class Cliente {

    private ServicoEventos servico;
    private ClienteEventosImpl cliente;
    private Object cliente_corba;

    public Cliente(String[] args) {
        System.out.println("Bem vindo ao Cliente eventos aleatorios");
        InicializaCorba(args);
        try {
            MenuPrincipal();
        } catch (IOException ex) {
            Logger.getLogger(Detector.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Falha de IO, por favor reinicie a aplicacao");
        }
    }

    private void InicializaCorba(String[] args) {
        try {
            cliente = new ClienteEventosImpl();

            // Inicializa o ORB
            ORB orb = ORB.init(args, null);

            // Ativa o POA
            POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            poa.the_POAManager().activate();

            // Obtém a referência do servidor de nomes (NameService)
            // Essa tarefa é realizada pelo ORB (resolve_initial_references)
            NamingContextExt nc = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));

            // Obtém o objeto CORBA (genérico) do servidor HelloWorld
            // É o servidor de nomes que fornece essa referência (resolve)
            org.omg.CORBA.Object o = nc.resolve(nc.to_name("ServicoEventos.corba"));

            // Transforma o objeto CORBA genérico num objeto CORBA ServicoEventos
            this.servico = ServicoEventosHelper.narrow(o);

            cliente_corba = poa.servant_to_reference(cliente);

        } catch (Exception e) {
            e.printStackTrace();
            this.servico = null;
        }
    }

    public void MenuPrincipal() throws IOException {

        while (true) {

            System.out.println("1- Registrar para receber evento");
            System.out.println("2- Receber um evento qualquer");
            System.out.println("3- Ver lista de eventos");
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

            String comando = stdin.readLine();

            if (comando.startsWith("1")) {
                RegistrarEvento();
            } else if (comando.startsWith("2")) {
                ReceberEventoQualquer();
            } else if (comando.startsWith("3")) {
                MostrarListaEventos();
            } else {
                msgOpcaoInvalida();
            }

        }

    }

    /**
     * Mostra uma lista com eventos disponíveis e depois volta ao menu principal
     */
    private void MostrarListaEventos() {
        System.out.println("Lista de eventos disponiveis: ");
        listaEventosHolder listaEventos = new listaEventosHolder();
        servico.ObterListaEventos(listaEventos);
        String[] valores = listaEventos.value;
        if (valores != null && valores.length > 0) {
            for (String valor : valores) {
                System.out.println(valor);
            }
        }

    }

    /**
     * Recebe um evento qualquer disparado e depois volta ao menu principal
     */
    @SuppressWarnings("empty-statement")
    private void ReceberEventoQualquer() {

        System.out.println("Aguardando evento qualquer...");
        StringHolder eventoQualquer = new StringHolder();
        while(!this.servico.obterEventoQualquer(eventoQualquer)) {
            System.out.println("falhou... tentando novamente...");
        }
        String eventoQualquerStr = eventoQualquer.value;
        System.out.println("Evento qualquer recebido: " + eventoQualquerStr);

    }

    /**
     * Registra para receber informações quando um determinado evento for disparado
     * e depois retorna ao menu principal
     */
    private void RegistrarEvento() throws IOException {
        System.out.println("Digite o evento que voce quer receber: ");
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        String comando = stdin.readLine();
        servico.MeRegistre(cliente_corba, comando);
    }

    /**
     * Avisa sobre uma Opção Inválida selecionada no menu principal
     */
    private void msgOpcaoInvalida() {
        System.out.println("Opcao invalida, tente novamente!");
    }

    public static void main(String[] args) {

        // Carrega o cliente e o resto é com ele!!!
        Cliente cliente = new Cliente(args);
        System.exit(0);
    }
}