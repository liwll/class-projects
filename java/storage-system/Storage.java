import java.io.Serializable;

/**
 * This class represents a Storage box registered with a company,
 * which contains the following fields: the id, the client, and the contents.
 * It implements the Serializable interface.
 *
 * @author
 * liwll
 */
public class Storage implements Serializable {
    private static long serialVersionUID;
    private int id;
    private String client;
    private String contents;

    /**
     * The default constructor for a Storage
     */
    public Storage() {}

    /**
     * Custom constructor for Storage which accepts parameters
     * @param id
     *  The unique int ID of the Storage
     * @param client
     *  The String name of the client who's storing the box
     * @param contents
     *  A String description of the contents of the box
     */
    public Storage(int id, String client, String contents) {
        this.id = id;
        this.client = client;
        this.contents = contents;
    }

    /**
     * Getter method for the ID
     * @return
     *  A unique int ID of the Storage box
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method for the client
     * @return
     *  The String name of the client storing the box
     */
    public String getClient() {
        return client;
    }

    /**
     * Getter method for the contents
     * @return
     *  A String description of the contents of the box
     */
    public String getContents() {
        return contents;
    }

    /**
     * Setter method for the ID
     * @param id
     *  A unique int ID for the Storage box
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter method for the client
     * @param client
     *  A String name of the client storing the Storage box
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * Setter method for the contents of the box
     * @param contents
     *  A String description of the contents of the box
     */
    public void setContents(String contents) {
        this.contents = contents;
    }
}
