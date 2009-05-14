package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author brodock
 */
public class Save implements Serializable {
    private HashMap<String, ArrayList<org.omg.CORBA.Object>> hashmap;
    private ArrayList<org.omg.CORBA.Object> detectores;

    public ArrayList<org.omg.CORBA.Object> getDetectores() {
        return detectores;
    }

    public void setDetectores(ArrayList<org.omg.CORBA.Object> detectores) {
        this.detectores = detectores;
    }

    public HashMap<String, ArrayList<org.omg.CORBA.Object>> getHashmap() {
        return hashmap;
    }

    public void setHashmap(HashMap<String, ArrayList<org.omg.CORBA.Object>> hashmap) {
        this.hashmap = hashmap;
    }

    

}
