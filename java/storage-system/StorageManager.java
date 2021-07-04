import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class represents a manager which allows the user to perform different
 * functions on the storage database, such as adding and removing boxes,
 * searching for boxes by ID, and listing boxes from a client.
 * It also allows users to save, load, and delete their changes.
 * It has one private static field, the storageTable database.
 *
 * @author
 * liwll
 */
public class StorageManager {
    private static StorageTable storageTable;

    /**
     * Main method for the class. It attempts to load saved data, if there
     * is no data it initializes a new table. Then, it opens the menu.
     * @param args
     *  Optional args to be used in this method
     */
    public static void main(String[] args) {
        try {
            FileInputStream fileIn = new FileInputStream("storage.obj");
            ObjectInputStream inStream = new ObjectInputStream(fileIn);
            storageTable = (StorageTable) inStream.readObject();
            inStream.close();
        }
        catch (FileNotFoundException e) {
            storageTable = new StorageTable();
        }
        catch (IOException e) {
            storageTable = new StorageTable();
        }
        catch (ClassNotFoundException e) {
            storageTable = new StorageTable();
        }

        System.out.print("Hello, and welcome to Rocky "
                + "Stream Storage Manager\n\n");
        menu();
    }

    /**
     * Method which prints the menu and allows the user to select
     * different functions for the database.
     */
    public static void menu() {
        String menu = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n",
                "P - Print all storage boxes",
                "A - Insert into storage box",
                "R - Remove contents from a storage box",
                "C - Select all boxes owned by a particular client",
                "F - Find a box by ID and display its owner and contents",
                "Q - Quit and save workspace",
                "X - Quit and delete workspace");
        Scanner userIn = new Scanner(System.in);
        System.out.printf("%s\n%s", menu, "Please select an option: ");
        String option = userIn.next().toUpperCase();

        switch (option) {
            case "P":
                menuPrint();
                menu();
                break;
            case "A":
                menuInsert();
                menu();
                break;
            case "R":
                menuRemove();
                menu();
                break;
            case "C":
                menuSelect();
                menu();
                break;
            case "F":
                menuFind();
                menu();
                break;
            case "Q":
                quitSave();
                break;
            case "X":
                quitDelete();
                break;
            default:
                System.out.print("Invalid option, please try again.\n\n");
                menu();
        }
    }

    /**
     * Method which prints all the storage boxes
     */
    public static void menuPrint() {
        System.out.printf("\n%s\n", storageTable);
    }

    /**
     * Method which inserts a box into the database,
     * allowing the user to specify the ID, client, and contents
     */
    public static void menuInsert() {
        try {
            Scanner userIn = new Scanner(System.in);
            System.out.print("Please enter ID: ");
            int id = userIn.nextInt();
            userIn.nextLine();
            System.out.print("Please enter client: ");
            String client = userIn.nextLine();
            System.out.print("Please enter contents: ");
            String contents = userIn.nextLine();

            Storage newStorage = new Storage(id, client, contents);
            storageTable.putStorage(newStorage.getId(), newStorage);
            System.out.printf("\nStorage %d set!\n\n", newStorage.getId());
        }
        catch (InputMismatchException e) {
            System.out.print("Input mismatch, please try again.\n\n");
        }
        catch (IllegalArgumentException e) {
            System.out.print("Illegal argument, please try again.\n\n");
        }
    }

    /**
     * Method which removes a box from the database,
     * allowing the user to specify the ID of the box to be removed
     */
    public static void menuRemove() {
        try {
            Scanner userIn = new Scanner(System.in);
            System.out.print("Please enter ID: ");
            int id = userIn.nextInt();
            if (storageTable.remove(id) != null)
                System.out.printf("Box %d is now removed.\n\n", id);
            else
                System.out.print("Couldn't find a box "
                        + "with that ID, please try again.\n\n");
        }
        catch (InputMismatchException e) {
            System.out.print("Input mismatch, please try again.\n\n");
        }
    }

    /**
     * Method which allows users to select,
     * and print the storage boxes from a specific client
     */
    public static void menuSelect() {
        Scanner userIn = new Scanner(System.in);
        System.out.print("Please enter the name of the client: ");
        String client = userIn.nextLine();
        System.out.printf("\n%s\n", storageTable.toString(client));
    }

    /**
     * Method which finds and prints a storage box,
     * allowing the user to specify the ID of the box to be printed
     */
    public static void menuFind() {
        try {
            Scanner userIn = new Scanner(System.in);
            System.out.print("Please enter ID: ");
            int id = userIn.nextInt();
            System.out.printf("Box %d\nContents: %s\nOwner: %s\n\n", id,
                    storageTable.getStorage(id).getContents(),
                    storageTable.getStorage(id).getClient());
        }
        catch (InputMismatchException e) {
            System.out.print("Input mismatch, please try again.\n\n");
        }
        catch (NullPointerException e) {
            System.out.print("Couldn't find a box "
                    + "with that ID, please try again.\n\n");
        }
    }

    /**
     * Method which allows the user the save the data and then quit
     */
    public static void quitSave() {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("storage.obj");
            ObjectOutputStream outStream = new ObjectOutputStream(fileOut);
            outStream.writeObject(storageTable);
            outStream.close();

            System.out.print("Storage Manager is quitting, "
                    + "current storage is saved for next session.\n\n");
        }
        catch (FileNotFoundException e) {
            System.out.print("Error, file could not be saved.\n\n");
        }
        catch (IOException e) {
            System.out.print("Error, file could not be saved.\n\n");
        }
    }

    /**
     * Method which allows the user to delete the data and then quit
     */
    public static void quitDelete() {
        File storageFile = new File("storage.obj");
        storageFile.delete();
        System.out.print("Storage Manager is quitting, "
                + "all data is being erased.\n\n");
    }
}
