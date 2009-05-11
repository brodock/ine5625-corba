package servico;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omg.CORBA.*;
import org.omg.CORBA.Object;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.*;

public class Servidor {

    private boolean backup = false;
    private Object servico_corba_obj;
    private ServicoEventos servidorBackup;
    private NamingContextExt nc;

    public boolean isBackup() {
        return backup;
    }

    public ServicoEventos getServidorBackup() {
        while (servidorBackup == null) {
            try {
                System.out.println("Procurando servidor de backup...");
                LocalizaServidorBackup();
                System.out.println("Servidor backup, localizado!");
            } catch (Exception ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
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
     * Registra o objeto ServicoEventos no servidor de nomes
     * @param backup Este servidor é um servidor de backup?
     * @throws org.omg.CosNaming.NamingContextPackage.NotFound
     * @throws org.omg.CosNaming.NamingContextPackage.CannotProceed
     * @throws org.omg.CosNaming.NamingContextPackage.InvalidName
     */
    public void RegistraServico(boolean backup) throws NotFound, CannotProceed, InvalidName {


        // Registra a referência objCORBA no servidor de nomes usando o bind (pode ser tb com rebind)
        if (backup) {
            nc.rebind(nc.to_name("ServicoEventosBackup.corba"), servico_corba_obj);
            System.out.println("Carregado como servidor de BACKUP");
        } else {
            nc.rebind(nc.to_name("ServicoEventos.corba"), servico_corba_obj);
            System.out.println("Carregado como servidor PRINCIPAL");
        }

        this.backup = backup;
    }

    public Servidor(String[] args) {
        System.out.println("Servidor do Servico de Eventos CORBA");

        for (int i = 0; i < args.length; i++) {
            String param = args[i];
            if (param.equals("-backup")) {
                this.backup = true;
                args[i] = null;
            }

        }
        this.InicializaCorba(args);

    }

    /**
     * Transforma o serviço em execução no serviço principal
     */
    public void virarServidorPrincipal() {
        System.out.println("Tentando virar servidor PRINCIPAL...");
        this.backup = false;
        try {
            this.RegistraServico(this.backup);
        } catch (Exception ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor(args);
        System.exit(0);
    }

    /**
     * Inicializa o ORB e objetos corba, registra o servidor no servico de nomes
     * @param args Parametros de inicialização do ORB
     */
    private void InicializaCorba(String[] args) {
        // Inicializa o ORB
        ORB orb = ORB.init(args, null);
        try {

            // Ativa o POA
            POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            poa.the_POAManager().activate();

            // Instancia um objeto da classe GoodDayImpl
            ServicoEventosImpl servicoEventos = new ServicoEventosImpl(this);

            // Transforma o objeto java ServicoEventosImpl (servicoEventos) num objeto CORBA genérico (objCORBA)
            this.servico_corba_obj = poa.servant_to_reference(servicoEventos);

            // Obtém a referência (endereço) do servidor de nomes (NameService)
            // Essa tarefa é realizada pelo ORB (orb.resolve_initial_references)
            this.nc = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));

            RegistraServico(backup);

            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Procura o servidor de backup e registra ele para utilização posterior
     * 
     * @throws org.omg.CosNaming.NamingContextPackage.InvalidName
     * @throws org.omg.CosNaming.NamingContextPackage.CannotProceed
     * @throws org.omg.CosNaming.NamingContextPackage.NotFound
     */
    private void LocalizaServidorBackup() throws InvalidName, CannotProceed, NotFound {

        org.omg.CORBA.Object servidor = nc.resolve(nc.to_name("ServicoEventosBackup.corba"));
        this.servidorBackup = ServicoEventosHelper.narrow(servidor);
    }
}