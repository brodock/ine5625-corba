package cliente;

/**
 * Cliente de Eventos
 * @author brodock
 */
public class ClienteEventosImpl extends ClienteEventosPOA {

    public boolean NovoAlerta(String evento) {

        System.out.println("["+evento+"] recebido");
        return true;
    }

}
