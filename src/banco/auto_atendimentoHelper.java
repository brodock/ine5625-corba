package banco;


/**
* banco/auto_atendimentoHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Banco.idl
* Quarta-feira, 25 de Março de 2009 21h10min03s BRT
*/

abstract public class auto_atendimentoHelper
{
  private static String  _id = "IDL:banco/auto_atendimento:1.0";

  public static void insert (org.omg.CORBA.Any a, banco.auto_atendimento that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static banco.auto_atendimento extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (banco.auto_atendimentoHelper.id (), "auto_atendimento");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static banco.auto_atendimento read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_auto_atendimentoStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, banco.auto_atendimento value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static banco.auto_atendimento narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof banco.auto_atendimento)
      return (banco.auto_atendimento)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      banco._auto_atendimentoStub stub = new banco._auto_atendimentoStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static banco.auto_atendimento unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof banco.auto_atendimento)
      return (banco.auto_atendimento)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      banco._auto_atendimentoStub stub = new banco._auto_atendimentoStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
