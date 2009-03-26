package banco;


/**
* banco/_auto_atendimentoStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Banco.idl
* Quarta-feira, 25 de Março de 2009 21h10min03s BRT
*/

public class _auto_atendimentoStub extends org.omg.CORBA.portable.ObjectImpl implements banco.auto_atendimento
{

  public String boas_vindas ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_boas_vindas", true);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return boas_vindas (        );
            } finally {
                _releaseReply ($in);
            }
  } // boas_vindas

  public double saldo (int c) throws banco.conta_inval
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("saldo", true);
                banco.contaHelper.write ($out, c);
                $in = _invoke ($out);
                double $result = banco.valorHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:banco/conta_inval:1.0"))
                    throw banco.conta_invalHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return saldo (c        );
            } finally {
                _releaseReply ($in);
            }
  } // saldo

  public void extrato (int c, banco.transacoesHolder t, org.omg.CORBA.DoubleHolder saldo) throws banco.conta_inval
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("extrato", true);
                banco.contaHelper.write ($out, c);
                $in = _invoke ($out);
                t.value = banco.transacoesHelper.read ($in);
                saldo.value = banco.valorHelper.read ($in);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:banco/conta_inval:1.0"))
                    throw banco.conta_invalHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                extrato (c, t, saldo        );
            } finally {
                _releaseReply ($in);
            }
  } // extrato

  public void tranferencia (int origem, int destino, double v) throws banco.conta_inval, banco.saldo_insuf
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("tranferencia", true);
                banco.contaHelper.write ($out, origem);
                banco.contaHelper.write ($out, destino);
                banco.valorHelper.write ($out, v);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:banco/conta_inval:1.0"))
                    throw banco.conta_invalHelper.read ($in);
                else if (_id.equals ("IDL:banco/saldo_insuf:1.0"))
                    throw banco.saldo_insufHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                tranferencia (origem, destino, v        );
            } finally {
                _releaseReply ($in);
            }
  } // tranferencia

  public void investimento (int c, banco.aplicacao apl, double v) throws banco.conta_inval, banco.saldo_insuf
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("investimento", true);
                banco.contaHelper.write ($out, c);
                banco.aplicacaoHelper.write ($out, apl);
                banco.valorHelper.write ($out, v);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:banco/conta_inval:1.0"))
                    throw banco.conta_invalHelper.read ($in);
                else if (_id.equals ("IDL:banco/saldo_insuf:1.0"))
                    throw banco.saldo_insufHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                investimento (c, apl, v        );
            } finally {
                _releaseReply ($in);
            }
  } // investimento

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:banco/auto_atendimento:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args, props).string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     String str = org.omg.CORBA.ORB.init (args, props).object_to_string (this);
     s.writeUTF (str);
  }
} // class _auto_atendimentoStub
