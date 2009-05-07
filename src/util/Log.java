package util;

import org.omg.CORBA.Object;

/**
 * Objeto Log representa o registro de alguma requisição realizada
 * ao servidor central e replicada para o servidor de backup
 * 
 * @author brodock
 */
public class Log {

    private String copiaRequisicao;
    private String evento;
    private org.omg.CORBA.Object ref;

    public Log(String copiaRequisicao, String evento, Object ref) {
        this.copiaRequisicao = copiaRequisicao;
        this.evento = evento;
        this.ref = ref;
    }

    public String getCopiaRequisicao() {
        return copiaRequisicao;
    }

    public String getEvento() {
        return evento;
    }

    public Object getRef() {
        return ref;
    }
}
