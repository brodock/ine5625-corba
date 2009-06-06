package util;
import java.io.*;

/**
 * Classe utilitária para lidar com objetos corba
 * @author brodock
 */
public class ObjectUtils {

    /**
     * Deserializa um array de bytes para um objeto
     * @param b
     * @return
     */
    public static Object deserialize(byte b[]) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Serializa um objeto qualquer para um array de bytes
     * @param o
     * @return
     */
    public static byte[] serialize(Object o) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            oos.flush();
            oos.close();
            return baos.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
