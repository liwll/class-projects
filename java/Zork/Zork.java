import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

/**
 * A class which runs the Zork game and allows users to play and edit the tree.
 *
 * @author
 * liwll
 */
public class Zork {
    private static String fileName;

    /**
     * The main method for Zork. It shows a welcome message and loads the tree.
     * @param args
     *  The optional args to be used in the method
     */
    public static void main(String[] args) {
        System.out.print("Hello and Welcome to Zork!\n\n");
        loadGame();
    }

    /**
     * A helper method for the main method. It loads the tree from a file.
     */
    public static void loadGame() {
        try {
            Scanner userIn = new Scanner(System.in);
            System.out.print("Please enter the file name: ");
            String file = userIn.nextLine();
            fileName = file;
            System.out.print("\nLoading game from file...\n\n");
            StoryTree tree = StoryTree.readTree(file);
            System.out.print("File loaded!\n\n");
            menu(tree);
        }
        catch (DataFormatException e) {
            System.out.print("Data format error, please try again.\n\n");
            loadGame();
        }
        catch (FileNotFoundException e) {
            System.out.print("File not found, please try again.\n\n");
            loadGame();
        }
        catch (IllegalArgumentException e) {
            System.out.print("Illegal argument, please try again.\n\n");
            loadGame();
        }
        catch (NullPointerException e) {
            System.out.print("Data format error, please try again.\n\n");
            loadGame();
        }
    }

    /**
     * A method which prints the different actions a player can take:
     * editing, playing, or quitting.
     * @param tree
     *  The tree which the menu is being used on
     */
    public static void menu(StoryTree tree) {
        System.out.print("Would you like to edit (E), play (P) or quit (Q)? ");
        Scanner userIn = new Scanner(System.in);
        String userChoice = userIn.nextLine().toUpperCase();
        try {
            tree.resetCursor();
            tree.selectChild("1");
            switch (userChoice) {
                case "E":
                    editTree(tree);
                    break;
                case "P":
                    playTree(tree);
                    break;
                case "Q":
                    saveTreeToFile(tree);
                    System.out.print("Program terminating normally.");
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        catch (IllegalArgumentException e) {
            System.out.print("\nIllegal argument, please try again.\n\n");
            menu(tree);
        }
        catch (InvalidArgumentException e) {
            System.out.print("Invalid argument, please try again.\n\n");
        }
        catch (NodeNotPresentException e) {
            System.out.println("Error, node not present.");
        }
    }

    /**
     * A method which provides an interface for a player to edit the tree.
     * @param tree
     *  The tree which is being edited
     */
    public static void editTree(StoryTree tree) {
        String editorMenu = String.format("\nZork Editor:\n    %s\n    %s"
                        + "\n    %s\n    %s\n    %s\n    %s"
                        + "\n    %s\n    %s\n    %s\n\n",
                "V: View the cursor's position, option and message.",
                "S: Select a child of this cursor (options are 1, 2, and 3).",
                "O: Set the option of the cursor.",
                "M: Set the message of the cursor.",
                "A: Add a child StoryNode to the cursor.",
                "D: Delete one of the cursor's children "
                        + "and all its descendants.",
                "R: Move the cursor to the root of the tree.",
                "P: Move the cursor to its parent (Extra Credit).",
                "Q: Quit editing and return to main menu.");
        System.out.print(editorMenu);

        Scanner userIn = new Scanner(System.in);
        System.out.print("Please select an option: ");
        String userOption = userIn.nextLine().toUpperCase();

        try {
            switch (userOption) {
                case "V":
                    viewCursor(tree);
                    editTree(tree);
                    break;
                case "S":
                    selectCursorChild(tree);
                    editTree(tree);
                    break;
                case "O":
                    setCursorOption(tree);
                    editTree(tree);
                    break;
                case "M":
                    setCursorMessage(tree);
                    editTree(tree);
                    break;
                case "A":
                    addCursorChild(tree);
                    editTree(tree);
                    break;
                case "D":
                    deleteCursorChild(tree);
                    editTree(tree);
                    break;
                case "R":
                    cursorToRoot(tree);
                    editTree(tree);
                    break;
                case "P":
                    returnToParent(tree);
                    editTree(tree);
                case "Q":
                    menu(tree);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        catch (IllegalArgumentException e) {
            System.out.print("\nIllegal argument, please try again.\n");
            editTree(tree);
        }
    }

    /**
     * A method which allows the player to play Zork, represented by the tree.
     * @param tree
     *  The tree which which contains the story to be played
     */
    public static void playTree(StoryTree tree) {
        try {
            Scanner userIn = new Scanner(System.in);
            tree.resetCursor();
            tree.selectChild("1");
            System.out.print("\n" + tree.getCursorOption() + "\n\n");
            while (tree.getGameState() == GameState.GAME_NOT_OVER) {
                System.out.println(tree.getCursorMessage());
                String[][] options = tree.getOptions();
                for (int i = 0; i < options.length; i++) {
                    System.out.println(String.format("%d) %s", i + 1,
                            options[i][1]));
                }
                System.out.print("Please make a choice. ");
                int choice = userIn.nextInt();
                System.out.println();
                String position = tree.getCursorPosition() + "-" + choice;
                tree.selectChild(position);
            }
            System.out.print(tree.getCursorMessage()
                    + "\n\nThanks for playing.\n\n");
        }
        catch (InvalidArgumentException e) {
            System.out.print("Invalid argument, please try again.\n\n");
        }
        catch (NodeNotPresentException e) {
            System.out.print("Choice not valid, please try again.\n\n");
        }
        catch (InputMismatchException e) {
            System.out.print("\nInput mismatch, please try again.\n\n");
        }
        menu(tree);
    }

    /**
     * A method the player can access from editTree. It prints information
     * about the cursor like the position, option, and message
     * @param tree
     *  The tree which contains the cursor to be viewed
     */
    public static void viewCursor(StoryTree tree) {
        System.out.print(String.format("Position: %s\n"
                + "Option: %s\nMessage: %s\n", tree.getCursorPosition(),
                tree.getCursorOption(), tree.getCursorMessage()));
    }

    /**
     * A method the player can access from editTree.
     * It allows the user to move the cursor to one of its children
     * @param tree
     *  The tree for which the cursor is to be moved
     */
    public static void selectCursorChild(StoryTree tree) {
        Scanner userIn = new Scanner(System.in);
        String choices;
        switch (tree.getOptions().length) {
            case 0:
                choices = "[node has no children]";
                break;
            case 1:
                choices = "[1]";
                break;
            case 2:
                choices = "[1,2]";
                break;
            case 3:
                choices = "[1,2,3]";
                break;
            default:
                choices = "[]";
        }
        System.out.print("Please select a child " + choices + ": ");
        int choice = -1;
        try {
            choice = userIn.nextInt();
            String position = tree.getCursorPosition() + "-" + choice;
            tree.selectChild(position);
        }
        catch (InvalidArgumentException e) {
            System.out.print("\nInvalid argument, child cannot be null.\n");
        }
        catch (NodeNotPresentException e) {
            System.out.print("\nError, no child " + choice
                    + " for the current node.\n");
        }
        catch (InputMismatchException e) {
            System.out.print("\nInvalid input type, please try again.\n");
        }
    }

    /**
     * A method the player can access from editTree.
     * It allows the player to change the option of the cursor
     * @param tree
     *  The tree which contains the cursor whose option will be changed
     */
    public static void setCursorOption(StoryTree tree) {
        Scanner userIn = new Scanner(System.in);
        System.out.print("Please enter a new option: ");
        String newOption = userIn.nextLine();
        tree.setCursorOption(newOption);
        System.out.print("\nOption set.\n");
    }

    /**
     * A method the player can access from editTree.
     * It allows the player to change the message of the cursor
     * @param tree
     *  The tree which contains the cursor whose message will be changed
     */
    public static void setCursorMessage(StoryTree tree) {
        Scanner userIn = new Scanner(System.in);
        System.out.print("Please enter a new message: ");
        String newMessage = userIn.nextLine();
        tree.setCursorMessage(newMessage);
        System.out.print("\nMessage set.\n");
    }

    /**
     * A method the player can access from editTree.
     * It adds a child to the cursor
     * @param tree
     *  The tree which contains the cursor where the child will be added to
     */
    public static void addCursorChild(StoryTree tree) {
        Scanner userIn = new Scanner(System.in);
        System.out.print("Enter an option: ");
        String childOption = userIn.nextLine();
        System.out.print("Enter a message: ");
        String childMessage = userIn.nextLine();
        try {
            tree.addChild(childOption, childMessage);
        }
        catch (InvalidArgumentException e) {
            System.out.print("Fields cannot be null, please try again.\n");
        }
        catch (TreeFullException e) {
            System.out.print("Tree is full, please try again.\n");
        }
    }

    /**
     * A method the player can access from editTree.
     * It deletes the child of a cursor
     * @param tree
     *  The tree which contains the cursor whose child will be deleted
     */
    public static void deleteCursorChild(StoryTree tree) {
        Scanner userIn = new Scanner(System.in);
        String choices;
        switch (tree.getOptions().length) {
            case 0:
                choices = "[node has no children]";
                break;
            case 1:
                choices = "[1]";
                break;
            case 2:
                choices = "[1,2]";
                break;
            case 3:
                choices = "[1,2,3]";
                break;
            default:
                choices = "[]";
        }
        System.out.print("Please select a child " + choices + ": ");
        int choice = -1;
        try {
            choice = userIn.nextInt();
            String position = tree.getCursorPosition() + "-" + choice;
            tree.removeChild(position);
            System.out.print("Subtree deleted.\n");
        }
        catch (NodeNotPresentException e) {
            System.out.print("\nError, no child " + choice
                    + " for the current node.\n");
        }
        catch (InputMismatchException e) {
            System.out.print("\nInvalid input type, please try again.\n");
        }
    }

    /**
     * A method the player can access from editTree.
     * It sets the cursor to the root of the tree (left child of the real root)
     * @param tree
     *  The tree which contains the cursor to be set
     */
    public static void cursorToRoot(StoryTree tree) {
        try {
            tree.resetCursor();
            tree.selectChild("1");
            System.out.print("\nCursor moved to root.\n");
        }
        catch (InvalidArgumentException e) {
            System.out.print("\nError, invalid argument.\n");
        }
        catch (NodeNotPresentException e) {
            System.out.print("\nError, node not present.\n");
        }
    }

    /**
     * EXTRA CREDIT
     * A method the player can access from editTree.
     * It returns the cursor to its parent
     * @param tree
     *  The tree which contains the cursor to be set
     */
    public static void returnToParent(StoryTree tree) {
        try {
            String position = tree.getCursorPosition();
            if (position.equals("1"))
                throw new IllegalArgumentException();
            String parentPosition =
                    position.substring(0, position.length() - 2);
            tree.resetCursor();
            tree.selectChild("1");
            String cursorPosition = "1";
            for (int i = 1; i < parentPosition.length(); i++) {
                cursorPosition = cursorPosition + parentPosition.charAt(i);
                switch (parentPosition.charAt(i)) {
                    case '1':
                    case '2':
                    case '3':
                        tree.selectChild(cursorPosition);
                        break;
                    default:
                        continue;
                }
            }
            System.out.print("\nCursor returned to parent.\n");
        }
        catch (InvalidArgumentException e) {
            System.out.print("\nError, invalid argument.\n");
        }
        catch (NodeNotPresentException e) {
            System.out.print("\nError, node not present.\n");
        }
        catch (IllegalArgumentException e) {
            System.out.print("\nCursor not allowed to return to real root.\n");
        }
    }

    /**
     * Method which saves the tree to the file it was read from.
     * It is called upon when the player quits from the menu.
     * @param tree
     *  The tree to be written to the file
     */
    public static void saveTreeToFile(StoryTree tree) {
        try {
            System.out.print("\nGame being saved to " + fileName + "...\n\n");
            tree.resetCursor();
            tree.selectChild("1");
            StoryTree.saveTree(fileName, tree);
            System.out.print("Save successful!\n\n");
        }
        catch (IllegalArgumentException e) {
            System.out.println("Illegal Argument error.");
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found error.");
        }
        catch (InvalidArgumentException e) {
            System.out.println("Error, invalid argument.");
        }
        catch (NodeNotPresentException e) {
            System.out.println("Error, node not present.");
        }
    }
}
