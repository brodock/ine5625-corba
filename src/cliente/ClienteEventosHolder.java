package cliente;

/**
 * ClienteEventosHolder.java .
 * Generated by the IDL-to-Java compiler (portable), version "3.2"
 * from ClienteEventos.idl
 * Quinta-feira, 30 de Abril de 2009 19h08min13s BRT
 */
public final class ClienteEventosHolder implements org.omg.CORBA.portable.Streamable {

    public ClienteEventos value = null;

    public ClienteEventosHolder() {
    }

    public ClienteEventosHolder(ClienteEventos initialValue) {
        value = initialValue;
    }

    public void _read(org.omg.CORBA.portable.InputStream i) {
        value = ClienteEventosHelper.read(i);
    }

    public void _write(org.omg.CORBA.portable.OutputStream o) {
        ClienteEventosHelper.write(o, value);
    }

    public org.omg.CORBA.TypeCode _type() {
        return ClienteEventosHelper.type();
    }
}
