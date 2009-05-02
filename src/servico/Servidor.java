package servico;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.omg.CORBA.*;
import org.omg.CORBA.Object;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.*;

public class Servidor {

    private boolean backup = false;
    private Object servidor_corba;
    private NamingContextExt nc;

    public boolean isBackup() {
        return backup;
    }

    public void DefineServidor(boolean backup) throws NotFound, CannotProceed, InvalidName {


        // Registra a referência objCORBA no servidor de nomes usando o bind (pode ser tb com rebind)
        if (backup) {
            nc.rebind(nc.to_name("ServicoEventosBackup.corba"), servidor_corba);
            System.out.println("Carregado como servidor de BACKUP");
        } else {
            nc.rebind(nc.to_name("ServicoEventos.corba"), servidor_corba);
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
            this.servidor_corba = poa.servant_to_reference(servicoEventos);

            // Obtém a referência (endereço) do servidor de nomes (NameService)
            // Essa tarefa é realizada pelo ORB (orb.resolve_initial_references)
            this.nc = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));

            DefineServidor(backup);

            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor(args);
        System.exit(0);
    }
}