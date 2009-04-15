package detector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import servico.*;

/**
 *
 * @author brodock
 */
public class Detector {

    private ServicoEventos servico;
    private ArrayList<String> eventos = new ArrayList<String>();

    /**
     * Inicializa um detector passando parametros de conexão do CORBA
     * @param args Parametros do usuário para conexão CORBA ao ORB
     */
    public Detector(String[] args) {
        System.out.println("Bem vindo ao Detector de eventos aleatórios");
        InicializaCorba(args);
        try {
            MenuPrincipal();
        } catch (IOException ex) {
            Logger.getLogger(Detector.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Falha de IO, por favor reinicie a aplicação");
        }
    }

    private void InicializaCorba(String[] args) {
        try {

            // Inicializa o ORB
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);

            // Obtém a referência do servidor de nomes (NameService)
            // Essa tarefa é realizada pelo ORB (resolve_initial_references)
            NamingContextExt nc = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));

            // Obtém o objeto CORBA (genérico) do servidor HelloWorld
            // É o servidor de nomes que fornece essa referência (resolve)
            org.omg.CORBA.Object o = nc.resolve(nc.to_name("ServicoEventos.corba"));

            // Transforma o objeto CORBA genérico num objeto CORBA ServicoEventos
            this.servico = ServicoEventosHelper.narrow(o);

        } catch (Exception e) {
            e.printStackTrace();
            this.servico = null;
        }
    }

    public void MenuPrincipal() throws IOException {

        while (true) {
            System.out.println("1- Definir novo evento");
            System.out.println("2- Ativar evento existente");
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

            String comando = stdin.readLine();

            if (comando.startsWith("1")) {
                DefinirEvento();
            } else if (comando.startsWith("2")) {
                AtivarEvento();
            } else {
                msgOpcaoInvalida();
            }

        }

    }

    private void msgOpcaoInvalida() {
        System.out.println("Opção inválida, tente novamente!");
    }

    public static void main(String[] args) {
        Detector detector = new Detector(args);
        System.exit(0);
    }

    private void AtivarEvento() throws IOException {

        System.out.println("Digite o evento que será ativado: ");

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String comando = stdin.readLine();

        this.servico.NovoEvento(comando);
    }

    private void DefinirEvento() throws IOException {
        System.out.println("Digite o evento que será adicionado: ");

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String comando = stdin.readLine();

        this.servico.CadastrarEvento(comando);
    }
}
