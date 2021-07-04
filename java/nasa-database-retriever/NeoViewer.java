import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class which allows viewers to create and manage a database,
 * add pages, sort the data, and print the database.
 *
 * liwll
 */
public class NeoViewer {
    /**
     * Main method for the class, it creates a database and opens the menu
     * @param args
     *  The main args
     */
    public static void main(String[] args) {
        System.out.printf("Welcome to NEO Viewer!\n\n");
        NeoDatabase database = new NeoDatabase();
        menu(database);
    }

    /**
     * Method which prints a menu and allows the user to select an option
     * @param database
     *  The database being operated upon
     */
    public static void menu(NeoDatabase database) {
        String menu = String.format("%s\n%s\n%s\n%s\n%s\n",
                "Option Menu:",
                "  A) Add a page to the database",
                "  S) Sort the database",
                "  P) Print the database as a table",
                "  Q) Quit");

        Scanner userIn = new Scanner(System.in);
        System.out.printf("%s\n%s", menu, "Select a menu option: ");
        String userOption = userIn.nextLine();

        try {
            switch (userOption.toUpperCase()) {
                case "A":
                    addPage(database);
                    menu(database);
                    break;
                case "S":
                    sortDatabase(database);
                    menu(database);
                    break;
                case "P":
                    printDatabase(database);
                    menu(database);
                    break;
                case "Q":
                    System.out.print("Program terminating normally...");
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        catch (IllegalArgumentException e) {
            System.out.print("Illegal argument, please try again.\n\n");
            menu(database);
        }
    }

    /**
     * Method which allows the user to add a page to the database
     * @param database
     *  The database being operated upon
     */
    public static void addPage(NeoDatabase database) {
        try {
            Scanner userIn = new Scanner(System.in);
            System.out.printf("\nEnter the page to load: ");
            int pageNum = userIn.nextInt();

            String url = database.buildQueryURL(pageNum);
            database.addAll(url);
            System.out.print("\nPage loaded successfully!\n\n");
        }
        catch (InputMismatchException e) {
            System.out.print("Input mismatch, please try again.\n\n");
        }
    }

    /**
     * Method which allows the user to sort the database
     * @param database
     *  The database being operated upon
     */
    public static void sortDatabase(NeoDatabase database) {
        Scanner userIn = new Scanner(System.in);

        System.out.printf("\n%s\n%s\n%s\n%s\n\n%s",
                "R) Sort by referenceID",
                "D) Sort by diameter",
                "A) Sort by approach date",
                "M) Sort by miss distance",
                "Select menu option: ");
        String userOption = userIn.nextLine();

        String fullOption;

        try {
            switch (userOption.toUpperCase()) {
                case "R":
                    fullOption = "referenceID";
                    ReferenceIDComparator idComp = new ReferenceIDComparator();
                    database.sort(idComp);
                    break;
                case "D":
                    fullOption = "diameter";
                    DiameterComparator diameterComp = new DiameterComparator();
                    database.sort(diameterComp);
                    break;
                case "A":
                    fullOption = "approach date";
                    ApproachDateComparator dateComp =
                            new ApproachDateComparator();
                    database.sort(dateComp);
                    break;
                case "M":
                    fullOption = "miss distance";
                    MissDistanceComparator missComp = new
                            MissDistanceComparator();
                    database.sort(missComp);
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            System.out.printf("\nTable sorted on %s.\n\n", fullOption);
        }
        catch (IllegalArgumentException e) {
            System.out.print("Illegal argument, please try again.\n\n");
        }
    }

    /**
     * Method which allows the user to print the database
     * @param database
     *  The database being operated upon
     */
    public static void printDatabase(NeoDatabase database) {
        System.out.println();
        database.printTable();
        System.out.printf("\n\n");
    }
}
