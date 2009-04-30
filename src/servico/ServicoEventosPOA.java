package servico;

/**
 * ServicoEventosPOA.java .
 * Generated by the IDL-to-Java compiler (portable), version "3.2"
 * from ServicoEventos.idl
 * Terça-feira, 28 de Abril de 2009 20h14min44s BRT
 */
public abstract class ServicoEventosPOA extends org.omg.PortableServer.Servant
        implements ServicoEventosOperations, org.omg.CORBA.portable.InvokeHandler {

    // Constructors
    private static java.util.Hashtable _methods = new java.util.Hashtable();

    static {
        _methods.put("MeRegistre", new java.lang.Integer(0));
        _methods.put("ObterListaEventos", new java.lang.Integer(1));
        _methods.put("NovoEvento", new java.lang.Integer(2));
        _methods.put("CadastrarEvento", new java.lang.Integer(3));
        _methods.put("obterEventoQualquer", new java.lang.Integer(4));
        _methods.put("checkpoint", new java.lang.Integer(5));
        _methods.put("log", new java.lang.Integer(6));
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
            case 0: // ServicoEventos/MeRegistre
            {
                org.omg.CORBA.Object ref = org.omg.CORBA.ObjectHelper.read(in);
                String evento = in.read_string();
                boolean $result = false;
                $result = this.MeRegistre(ref, evento);
                out = $rh.createReply();
                out.write_boolean($result);
                break;
            }

            case 1: // ServicoEventos/ObterListaEventos
            {
                listaEventosHolder lista = new listaEventosHolder();
                this.ObterListaEventos(lista);
                out = $rh.createReply();
                listaEventosHelper.write(out, lista.value);
                break;
            }

            case 2: // ServicoEventos/NovoEvento
            {
                String evento = in.read_string();
                boolean $result = false;
                $result = this.NovoEvento(evento);
                out = $rh.createReply();
                out.write_boolean($result);
                break;
            }

            case 3: // ServicoEventos/CadastrarEvento
            {
                String evento = in.read_string();
                boolean $result = false;
                $result = this.CadastrarEvento(evento);
                out = $rh.createReply();
                out.write_boolean($result);
                break;
            }

            case 4: // ServicoEventos/obterEventoQualquer
            {
                org.omg.CORBA.StringHolder evento = new org.omg.CORBA.StringHolder();
                boolean $result = false;
                $result = this.obterEventoQualquer(evento);
                out = $rh.createReply();
                out.write_boolean($result);
                out.write_string(evento.value);
                break;
            }

            case 5: // ServicoEventos/checkpoint
            {
                byte estado[] = arrayDeBytesHelper.read(in);
                boolean $result = false;
                $result = this.checkpoint(estado);
                out = $rh.createReply();
                out.write_boolean($result);
                break;
            }

            case 6: // ServicoEventos/log
            {
                String copiaRequisicao = in.read_string();
                String evento = in.read_string();
                org.omg.CORBA.Object ref = org.omg.CORBA.ObjectHelper.read(in);
                boolean $result = false;
                $result = this.log(copiaRequisicao, evento, ref);
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
        "IDL:ServicoEventos:1.0"};

    public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectId) {
        return (String[]) __ids.clone();
    }

    public ServicoEventos _this() {
        return ServicoEventosHelper.narrow(
                super._this_object());
    }

    public ServicoEventos _this(org.omg.CORBA.ORB orb) {
        return ServicoEventosHelper.narrow(
                super._this_object(orb));
    }
} // class ServicoEventosPOA
