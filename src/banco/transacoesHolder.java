package banco;


/**
* banco/transacoesHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Banco.idl
* Quarta-feira, 25 de Março de 2009 21h10min03s BRT
*/

public final class transacoesHolder implements org.omg.CORBA.portable.Streamable
{
  public banco.transacao value[] = null;

  public transacoesHolder ()
  {
  }

  public transacoesHolder (banco.transacao[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = banco.transacoesHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    banco.transacoesHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return banco.transacoesHelper.type ();
  }

}
