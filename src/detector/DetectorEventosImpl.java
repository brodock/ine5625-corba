package detector;

import org.omg.CORBA.Object;

/**
 * Detector de Eventos que estará presente como referência no servidor
 * com a finalidade exclusiva e única de ser informado sobre troca de servidor
 * principa.
 * @author brodock
 */
public class DetectorEventosImpl extends DetectorEventosPOA {

    public boolean TrocaServidor(Object ref) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
