package banco;


/**
* banco/conta_invalHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Banco.idl
* Quarta-feira, 25 de Março de 2009 21h10min03s BRT
*/

abstract public class conta_invalHelper
{
  private static String  _id = "IDL:banco/conta_inval:1.0";

  public static void insert (org.omg.CORBA.Any a, banco.conta_inval that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static banco.conta_inval extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [1];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ulong);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (banco.contaHelper.id (), "conta", _tcOf_members0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "c",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_exception_tc (banco.conta_invalHelper.id (), "conta_inval", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static banco.conta_inval read (org.omg.CORBA.portable.InputStream istream)
  {
    banco.conta_inval value = new banco.conta_inval ();
    // read and discard the repository ID
    istream.read_string ();
    value.c = istream.read_ulong ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, banco.conta_inval value)
  {
    // write the repository ID
    ostream.write_string (id ());
    ostream.write_ulong (value.c);
  }

}
