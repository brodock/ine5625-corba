package banco;

/**
* banco/aplicacaoHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Banco.idl
* Quarta-feira, 25 de Março de 2009 21h10min03s BRT
*/

public final class aplicacaoHolder implements org.omg.CORBA.portable.Streamable
{
  public banco.aplicacao value = null;

  public aplicacaoHolder ()
  {
  }

  public aplicacaoHolder (banco.aplicacao initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = banco.aplicacaoHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    banco.aplicacaoHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return banco.aplicacaoHelper.type ();
  }

}
