package servico;


/**
* ServicoEventosPackage/arrayDeBytesHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ServicoEventos.idl
* Ter�a-feira, 28 de Abril de 2009 20h14min44s BRT
*/

abstract public class arrayDeBytesHelper
{
  private static String  _id = "IDL:ServicoEventos/arrayDeBytes:1.0";

  public static void insert (org.omg.CORBA.Any a, byte[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static byte[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_octet);
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (servico.arrayDeBytesHelper.id (), "arrayDeBytes", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static byte[] read (org.omg.CORBA.portable.InputStream istream)
  {
    byte value[] = null;
    int _len0 = istream.read_long ();
    value = new byte[_len0];
    istream.read_octet_array (value, 0, _len0);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, byte[] value)
  {
    ostream.write_long (value.length);
    ostream.write_octet_array (value, 0, value.length);
  }

}
