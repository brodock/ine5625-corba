package servico;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import org.omg.CORBA.*;
import org.omg.CORBA.Object;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;

public class Servidor {

    public static void main(String[] args) {
        // Inicializa o ORB
        ORB orb = ORB.init(args, null);
        try {

            // Ativa o POA
            POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            poa.the_POAManager().activate();

            // Instancia um objeto da classe GoodDayImpl
            ServicoEventosImpl servicoEventos = new ServicoEventosImpl();

            // Transforma o objeto java HelloWorld (hw) num objeto CORBA genérico (o)
            Object objCORBA = poa.servant_to_reference(servicoEventos);

            // Obtém a referência (endereço) do servidor de nomes (NameService)
            // Essa tarefa é realizada pelo ORB (orb.resolve_initial_references)
            NamingContextExt nc = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));

            // Registra a referência objCORBA no servidor de nomes usando o bind (pode ser tb com rebind)
            nc.rebind(nc.to_name("Hello.example"), objCORBA);

            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}