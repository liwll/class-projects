import java.io.Serializable;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Arrays;

/**
 * This class represents a database of the Storages, by using HashTables.
 * It implements the Serializable interface.
 *
 * @author
 * liwll
 */
public class StorageTable extends Hashtable implements Serializable {
    private static int serialVersionUID;

    /**
     * The default constructor for a StorageTable
     */
    public StorageTable() {}

    /**
     * A method which adds a Storage box into the table using the key (ID)
     * @param storageId
     *  A unique int which will be used as the key for insertion
     * @param storage
     *  The Storage object to be inserted
     * @throws IllegalArgumentException
     *  When the storage ID is negative,
     *  storage is null, or the key already exists
     */
    public void putStorage(int storageId, Storage storage)
            throws IllegalArgumentException {
        if (storageId < 0 || storage == null)
            throw new IllegalArgumentException();

        Enumeration<Integer> keys = this.keys();
        while (keys.hasMoreElements()) {
            int id = keys.nextElement();
            if (storageId == id)
                throw new IllegalArgumentException();
        }

        put(storageId, storage);
    }

    /**
     * Method to retrieve a Storage box from the table
     * @param storageId
     *  The unique int ID, or key, of the Storage to be retrieved
     * @return
     *  The Storage that was retrieved using the key
     */
    public Storage getStorage(int storageId) {
        return (Storage) get(storageId);
    }

    /**
     * Method which returns a formatted String of all the boxes in the table
     * @return
     *  A formatted table, which lists all the Storage boxes,
     *  and their data such as the ID, client, and contents.
     */
    public String toString() {
        String labels = String.format("%-13s%-32s%s\n%s%s\n",
                "Box#", "Contents", "Owner",
                "--------------------------------",
                "--------------------------------");
        Enumeration<Integer> keys = this.keys();

        int[] idArray = new int[this.size()];
        int i = 0;
        while (keys.hasMoreElements()) {
            int id = keys.nextElement();
            idArray[i] = id;
            i++;
        }

        String table = "";
        Arrays.sort(idArray);
        for (int n = 0; n < idArray.length; n++) {
            table += String.format("%-13d%-32s%s\n", idArray[n],
                    getStorage(idArray[n]).getContents(),
                    getStorage(idArray[n]).getClient());
        }

        return labels + table;
    }

    /**
     * Method which returns a formatted String of all the boxes
     * from a particular client in the table
     * @param client
     *  The specified client of the Storage boxes
     * @return
     *  A formatted table, which lists all the Storage boxes from a client,
     *  and their data such as the ID, client, and contents.
     */
    public String toString(String client) {
        String labels = String.format("%-13s%-32s%s\n%s%s\n",
                "Box#", "Contents", "Owner",
                "--------------------------------",
                "--------------------------------");
        Enumeration<Integer> keys = this.keys();

        int[] idArray = new int[this.size()];
        int i = 0;
        while (keys.hasMoreElements()) {
            int id = keys.nextElement();
            idArray[i] = id;
            i++;
        }

        String table = "";
        Arrays.sort(idArray);
        for (int n = 0; n < idArray.length; n++) {
            if (getStorage(idArray[n]).getClient().equals(client)) {
                table += String.format("%-13d%-32s%s\n", idArray[n],
                        getStorage(idArray[n]).getContents(),
                        getStorage(idArray[n]).getClient());
            }
        }

        return labels + table;
    }
}
