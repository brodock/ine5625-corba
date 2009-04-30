package servico;

/**
 * ServicoEventosOperations.java .
 * Generated by the IDL-to-Java compiler (portable), version "3.2"
 * from ServicoEventos.idl
 * Terça-feira, 28 de Abril de 2009 20h14min44s BRT
 */
public interface ServicoEventosOperations {

    boolean MeRegistre(org.omg.CORBA.Object ref, String evento);

    void ObterListaEventos(listaEventosHolder lista);

    boolean NovoEvento(String evento);

    boolean CadastrarEvento(String evento);

    boolean obterEventoQualquer(org.omg.CORBA.StringHolder evento);

    boolean checkpoint(byte[] estado);

    boolean log(String copiaRequisicao, String evento, org.omg.CORBA.Object ref);
} // interface ServicoEventosOperations
