package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;

/**
 *
 * @author brodock
 */
public class Save implements Serializable {
    private HashMap<String, ArrayList<String>> compact_hashmap;
    private ArrayList<String> compact_detectores;

    public ArrayList<org.omg.CORBA.Object> getDetectores(ORB orb) {
        ArrayList<org.omg.CORBA.Object> detectores = new ArrayList<org.omg.CORBA.Object>();
        for (String detector : this.compact_detectores) {
            detectores.add(orb.string_to_object(detector));
        }
        return detectores;
    }

    public void setDetectores(ArrayList<org.omg.CORBA.Object> detectores, ORB orb) {
        this.compact_detectores = new ArrayList<String>();
        for(org.omg.CORBA.Object detector : detectores) {
            this.compact_detectores.add(orb.object_to_string(detector));
        }

    }

    public HashMap<String, ArrayList<org.omg.CORBA.Object>> getHashmap(ORB orb) {
        HashMap<String, ArrayList<org.omg.CORBA.Object>> hashmap = new HashMap<String, ArrayList<org.omg.CORBA.Object>>();

        Set<Entry<String, ArrayList<String>>> entrySet = this.compact_hashmap.entrySet();

        for (Iterator<Entry<String, ArrayList<String>>> it = entrySet.iterator(); it.hasNext();) {
            Entry<String, ArrayList<String>> entry = it.next();

            ArrayList<org.omg.CORBA.Object> arraylist = new ArrayList<org.omg.CORBA.Object>();
            for (String client : entry.getValue()) {
                arraylist.add(orb.string_to_object(client));
            }
            hashmap.put(entry.getKey(), arraylist);
        }
        return hashmap;
    }

    public void setHashmap(HashMap<String, ArrayList<org.omg.CORBA.Object>> hashmap, ORB orb) {
        this.compact_hashmap = new HashMap<String, ArrayList<String>>();

        Set<Entry<String,ArrayList<org.omg.CORBA.Object>>> entrySet = hashmap.entrySet();
        for (Iterator<Entry<String, ArrayList<org.omg.CORBA.Object>>> it = entrySet.iterator(); it.hasNext();) {
            Entry<String, ArrayList<Object>> entry = it.next();

            ArrayList<String> arraylist_compact = new ArrayList<String>();
            for (Object client : entry.getValue()) {
                arraylist_compact.add(orb.object_to_string(client));
            }
            this.compact_hashmap.put(entry.getKey(), arraylist_compact);
        }
    }
}
