package cliente;

import detector.Detector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import servico.ServicoEventos;
import servico.ServicoEventosHelper;

/**
 *
 * @author brodock
 */
public class Cliente {

    private ServicoEventos servico;

    public Cliente(String[] args) {
        System.out.println("Bem vindo ao Cliente eventos aleatórios");
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

    private void MostrarListaEventos() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void ReceberEventoQualquer() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void RegistrarEvento() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void msgOpcaoInvalida() {
        System.out.println("Opção inválida, tente novamente!");
    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente(args);
        System.exit(0);
    }
}