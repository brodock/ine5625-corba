package servico;

/**
 * ServicoEventosPackage/listaEventosHolder.java .
 * Generated by the IDL-to-Java compiler (portable), version "3.2"
 * from ServicoEventos.idl
 * Terça-feira, 31 de Março de 2009 19h59min33s BRT
 */
public final class listaEventosHolder implements org.omg.CORBA.portable.Streamable {

    public String value[] = null;

    public listaEventosHolder() {
    }

    public listaEventosHolder(String[] initialValue) {
        value = initialValue;
    }

    public void _read(org.omg.CORBA.portable.InputStream i) {
        value = servico.listaEventosHelper.read(i);
    }

    public void _write(org.omg.CORBA.portable.OutputStream o) {
        servico.listaEventosHelper.write(o, value);
    }

    public org.omg.CORBA.TypeCode _type() {
        return servico.listaEventosHelper.type();
    }
}