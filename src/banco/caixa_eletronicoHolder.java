package banco;

/**
* banco/caixa_eletronicoHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Banco.idl
* Quarta-feira, 25 de Março de 2009 21h10min03s BRT
*/

public final class caixa_eletronicoHolder implements org.omg.CORBA.portable.Streamable
{
  public banco.caixa_eletronico value = null;

  public caixa_eletronicoHolder ()
  {
  }

  public caixa_eletronicoHolder (banco.caixa_eletronico initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = banco.caixa_eletronicoHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    banco.caixa_eletronicoHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return banco.caixa_eletronicoHelper.type ();
  }

}
