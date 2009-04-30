package cliente;

import org.omg.CORBA.Object;

/**
 * Cliente de Eventos
 * @author brodock
 */
public class ClienteEventosImpl extends ClienteEventosPOA {

    private Cliente cliente;

    public ClienteEventosImpl(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Recebe um novo alerta disparado pelo Serviço Eventos
     * @param evento
     * @return
     */
    public boolean NovoAlerta(String evento) {

        System.out.println("["+evento+"] recebido");
        return true;
    }

    /**
     * Usado para trocar a referência do servidor principal
     *
     * @param ref Objeto CORBA com a referência do servidor principal
     * @return true se ocorrer tudo bem
     */
    public boolean TrocaServidor(Object ref) {
        this.cliente.DefineServidor(ref);
        return true;
    }

}
