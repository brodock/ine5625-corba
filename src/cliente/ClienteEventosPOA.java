package cliente;

/**
 * ClienteEventosPOA.java .
 * Generated by the IDL-to-Java compiler (portable), version "3.2"
 * from ClienteEventos.idl
 * Quinta-feira, 30 de Abril de 2009 19h08min13s BRT
 */
public abstract class ClienteEventosPOA extends org.omg.PortableServer.Servant
        implements ClienteEventosOperations, org.omg.CORBA.portable.InvokeHandler {

    // Constructors
    private static java.util.Hashtable _methods = new java.util.Hashtable();

    static {
        _methods.put("NovoAlerta", new java.lang.Integer(0));
        _methods.put("TrocaServidor", new java.lang.Integer(1));
    }

    public org.omg.CORBA.portable.OutputStream _invoke(String $method,
            org.omg.CORBA.portable.InputStream in,
            org.omg.CORBA.portable.ResponseHandler $rh) {
        org.omg.CORBA.portable.OutputStream out = null;
        java.lang.Integer __method = (java.lang.Integer) _methods.get($method);
        if (__method == null) {
            throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
        }

        switch (__method.intValue()) {
            case 0: // ClienteEventos/NovoAlerta
            {
                String evento = in.read_string();
                boolean $result = false;
                $result = this.NovoAlerta(evento);
                out = $rh.createReply();
                out.write_boolean($result);
                break;
            }

            case 1: // ClienteEventos/TrocaServidor
            {
                org.omg.CORBA.Object ref = org.omg.CORBA.ObjectHelper.read(in);
                boolean $result = false;
                $result = this.TrocaServidor(ref);
                out = $rh.createReply();
                out.write_boolean($result);
                break;
            }

            default:
                throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
        }

        return out;
    } // _invoke
    // Type-specific CORBA::Object operations
    private static String[] __ids = {
        "IDL:ClienteEventos:1.0"};

    public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectId) {
        return (String[]) __ids.clone();
    }

    public ClienteEventos _this() {
        return ClienteEventosHelper.narrow(
                super._this_object());
    }

    public ClienteEventos _this(org.omg.CORBA.ORB orb) {
        return ClienteEventosHelper.narrow(
                super._this_object(orb));
    }
} // class ClienteEventosPOA
