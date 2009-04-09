package servico;


import org.omg.CORBA.*;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;


/**
 * ServicoEventosHelper.java .
 * Generated by the IDL-to-Java compiler (portable), version "3.2"
 * from ServicoEventos.idl
 * Terça-feira, 31 de Março de 2009 19h59min33s BRT
 */
abstract public class ServicoEventosHelper {

    private static String _id = "IDL:ServicoEventos:1.0";

    public static void insert(Any a, ServicoEventos that) {
        OutputStream out = a.create_output_stream();
        a.type(type());
        write(out, that);
        a.read_value(out.create_input_stream(), type());
    }

    public static ServicoEventos extract(Any a) {
        return read(a.create_input_stream());
    }
    private static TypeCode __typeCode = null;

    synchronized public static TypeCode type() {
        if (__typeCode == null) {
            __typeCode = ORB.init().create_interface_tc(ServicoEventosHelper.id(), "ServicoEventos");
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static ServicoEventos read(InputStream istream) {
        return narrow(istream.read_Object(_ServicoEventosStub.class));
    }

    public static void write(OutputStream ostream, ServicoEventos value) {
        ostream.write_Object((Object) value);
    }

    public static ServicoEventos narrow(org.omg.CORBA.Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof ServicoEventos) {
            return (ServicoEventos) obj;
        } else if (!obj._is_a(id())) {
            throw new org.omg.CORBA.BAD_PARAM();
        } else {
            org.omg.CORBA.portable.Delegate delegate = ((ObjectImpl) obj)._get_delegate();
            _ServicoEventosStub stub = new _ServicoEventosStub();
            stub._set_delegate(delegate);
            return stub;
        }
    }

    public static ServicoEventos unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof ServicoEventos) {
            return (ServicoEventos) obj;
        } else {
                Delegate delegate = ((ObjectImpl) obj)._get_delegate();
            _ServicoEventosStub stub = new _ServicoEventosStub();
            stub._set_delegate(delegate);
            return stub;
        }
    }
}