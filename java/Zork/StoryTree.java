import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.zip.DataFormatException;

/**
 * This class represents a ternary tree which stores the StoryTreeNodes.
 * Each node represents a decision until the player reaches a leaf
 * and the game ends. There are fields for the root, cursor, and state.
 *
 * @author
 * liwll
 */
public class StoryTree {
    private StoryTreeNode root;
    private StoryTreeNode cursor;
    private GameState state;

    /**
     * Default constructor for a StoryTree,
     * initializes the root, cursor, and state
     */
    public StoryTree() {
        root = new StoryTreeNode("root", "root",
                "Hello, welcome to Zork!");
        cursor = root;
        state = GameState.GAME_NOT_OVER;
    }

    /**
     * Method which reads a file and creates a tree from the file
     * @param fileName
     *  The name of the file to be read
     * @return
     *  A StoryTree created from the information in the file
     * @throws IllegalArgumentException
     *  When the fileName is empty or null
     * @throws DataFormatException
     *  When the data in the file does not fit the specified format
     * @throws FileNotFoundException
     *  When the corresponding file cannot be found
     */
    public static StoryTree readTree(String fileName) throws
            IllegalArgumentException, DataFormatException,
            FileNotFoundException {
        if (fileName.equals("") || fileName == null)
            throw new IllegalArgumentException();

        StoryTree tree = new StoryTree();
        File treeFile = new File(fileName);
        Scanner fileInput = new Scanner(treeFile);

        while(fileInput.hasNextLine()) {
            String data = fileInput.nextLine();

            int formatCheck = 0;
            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) == '|')
                    formatCheck++;
            }
            if (formatCheck != 2)
                throw new DataFormatException();

            String[] splitStrings = data.split("\\|");

            StoryTreeNode node;
            String position = "";
            String option = "";
            String message = "";
            int i = 0;
            while (i < splitStrings.length) {
                position = splitStrings[i];
                position = position.substring(0, position.length() - 1);
                i++;
                option = splitStrings[i];
                option = option.substring(1, option.length() - 1);
                i++;
                message = splitStrings[i].substring(1);
                i++;
            }
            node = new StoryTreeNode(position, option, message);
            tree.resetCursor();

            for (int n = 0; n < position.length(); n++) {
                if (n == position.length() - 1) {
                    switch (position.charAt(n)) {
                        case '1':
                            tree.cursor.setLeftChild(node);
                            break;
                        case '2':
                            tree.cursor.setMiddleChild(node);
                            break;
                        case '3':
                            tree.cursor.setRightChild(node);
                            break;
                        default:
                            throw new DataFormatException();
                    }
                }
                else {
                    switch (position.charAt(n)) {
                        case '1':
                            tree.cursor = tree.cursor.getLeftChild();
                            break;
                        case '2':
                            tree.cursor = tree.cursor.getMiddleChild();
                            break;
                        case '3':
                            tree.cursor = tree.cursor.getRightChild();
                            break;
                        case '-':
                            continue;
                        default:
                            throw new DataFormatException();
                    }
                }
            }
        }
        fileInput.close();
        return tree;
    }

    /**
     * Method which uses the recursive helper method saveTreeHelper
     * in order to write the StoryTree to a file
     * @param fileName
     *  The name of the file to be saved to
     * @param tree
     *  The tree which is being saved
     * @throws IllegalArgumentException
     *  When the fileName is empty or null, or when the tree is null
     * @throws FileNotFoundException
     *  When the specified file cannot be found
     */
    public static void saveTree(String fileName, StoryTree tree) throws
            IllegalArgumentException, FileNotFoundException {
        if (fileName.equals("") || fileName == null || tree == null)
            throw new IllegalArgumentException();
        PrintWriter fileWriter = new PrintWriter(fileName);
        saveTreeHelper(fileWriter, tree.cursor);
        fileWriter.close();
    }

    /**
     * Recursive helper method for saveTree
     * Writes to the file by doing a preorder traversal
     * @param fileWriter
     *  A PrintWriter which allows the method to write to a file
     * @param cursor
     *  The cursor to traverse the tree
     */
    public static void saveTreeHelper(PrintWriter fileWriter,
                                      StoryTreeNode cursor) {
        StoryTreeNode origCursor = cursor;
        if (cursor != null) {
            String line = String.format("%s | %s | %s",
                    cursor.getPosition(), cursor.getOption(),
                    cursor.getMessage());
            fileWriter.println(line);
            if (cursor.getLeftChild() != null) {
                saveTreeHelper(fileWriter, origCursor.getLeftChild());
                if (cursor.getMiddleChild() != null) {
                    saveTreeHelper(fileWriter, origCursor.getMiddleChild());
                    if (cursor.getRightChild() != null) {
                        saveTreeHelper(fileWriter, origCursor.getRightChild());
                    }
                }
            }
        }
    }

    /**
     * Getter method for the state of the game
     * Determines the state by examining the cursor
     * @return
     *  The state of the game
     */
    public GameState getGameState() {
        if (cursor.isWinningNode())
            state = GameState.GAME_OVER_WIN;
        else if (cursor.isLosingNode())
            state = GameState.GAME_OVER_LOSE;
        else
            state = GameState.GAME_NOT_OVER;
        return state;
    }

    /**
     * Getter method for the position of the cursor
     * @return
     *  A String which specifies the current position of the cursor
     */
    public String getCursorPosition() {
        return cursor.getPosition();
    }

    /**
     * Getter method for the message of the cursor
     * @return
     *  A String which specifies the current message of the cursor
     */
    public String getCursorMessage() {
        return cursor.getMessage();
    }

    /**
     * Getter method for the positions and options of the cursor's children
     * @return
     *  An array of Strings which contains the positions and options of the
     *  cursor's children
     */
    public String[][] getOptions() {
        int numChildren = 0;
        if (cursor.getLeftChild() != null) {
            numChildren = 1;
            if (cursor.getMiddleChild() != null) {
                numChildren = 2;
                if (cursor.getRightChild() != null)
                    numChildren = 3;
            }
        }
        String[][] pairs = new String[numChildren][2];
        for (int i = 0; i < pairs.length; i++) {
            for (int j = 0; j < pairs[i].length; j++) {
                if (i == 0 && j == 0)
                    pairs[i][j] = cursor.getLeftChild().getPosition();
                if (i == 0 && j == 1)
                    pairs[i][j] = cursor.getLeftChild().getOption();
                if (i == 1 && j == 0)
                    pairs[i][j] = cursor.getMiddleChild().getPosition();
                if (i == 1 && j == 1)
                    pairs[i][j] = cursor.getMiddleChild().getOption();
                if (i == 2 && j == 0)
                    pairs[i][j] = cursor.getRightChild().getPosition();
                if (i == 2 && j == 1)
                    pairs[i][j] = cursor.getRightChild().getOption();
            }
        }
        return pairs;
    }

    /**
     * Getter method for the option of the cursor
     * @return
     *  A String which specifies the current option of the cursor
     */
    public String getCursorOption() {
        return cursor.getOption();
    }

    /**
     * Setter method for the message of the cursor
     * @param message
     *  The new message for the cursor
     */
    public void setCursorMessage(String message) {
        cursor.setMessage(message);
    }
    /**
     * Setter method for the option of the cursor
     * @param option
     *  The new option for the cursor
     */
    public void setCursorOption(String option) {
        cursor.setOption(option);
    }

    /**
     * Method which resets the cursor to the root node
     */
    public void resetCursor() {
        cursor = root;
    }

    /**
     * Method which moves the cursor to one of its children
     * @param position
     *  The position of the desired child of the cursor
     * @throws InvalidArgumentException
     *  When the position is null or empty
     * @throws NodeNotPresentException
     *  When the desired child does not exist
     */
    public void selectChild(String position) throws InvalidArgumentException,
            NodeNotPresentException{
        if (position == null || position.equals(""))
            throw new InvalidArgumentException();
        String[][] options = getOptions();
        int childIndex = -1;
        for (int i = 0; i < options.length; i++) {
            if (options[i][0].equals(position))
                childIndex = i;
        }
        switch (childIndex) {
            case 0:
                cursor = cursor.getLeftChild();
                break;
            case 1:
                cursor = cursor.getMiddleChild();
                break;
            case 2:
                cursor = cursor.getRightChild();
                break;
            default:
                throw new NodeNotPresentException();
        }
    }

    /**
     * Method which adds a child to the cursor
     * @param option
     *  The desired option for the added child
     * @param message
     *  The desired message for the added child
     * @throws InvalidArgumentException
     *  When either the option or message are empty or null
     * @throws TreeFullException
     *  When the cursor already has 3 children, and a child cannot be added
     */
    public void addChild(String option, String message) throws
            InvalidArgumentException, TreeFullException{
        if (option == null || message == null
                || option.equals("") || message.equals(""))
            throw new InvalidArgumentException();

        StoryTreeNode child;
        String parentPosition = getCursorPosition();
        String childPosition;
        if (cursor.getLeftChild() == null) {
            childPosition = parentPosition + "-1";
            child = new StoryTreeNode(childPosition, option, message);
            cursor.setLeftChild(child);
        }
        else if (cursor.getMiddleChild() == null) {
            childPosition = parentPosition + "-2";
            child = new StoryTreeNode(childPosition, option, message);
            cursor.setMiddleChild(child);
        }
        else if (cursor.getRightChild() == null) {
            childPosition = parentPosition + "-3";
            child = new StoryTreeNode(childPosition, option, message);
            cursor.setRightChild(child);
        }
        else
            throw new TreeFullException();
    }

    /**
     * Method which utilizes the recursive shift method in StoryTreeNode
     * in order to remove a child of the cursor
     * @param position
     *  The position of the child to be removed
     * @return
     *  A StoryTreeNode, the child that was removed
     * @throws NodeNotPresentException
     *  When the specified child does not exist
     */
    public StoryTreeNode removeChild(String position)
            throws NodeNotPresentException{
        if (position == null)
            throw new NodeNotPresentException();
        String[][] options = getOptions();
        int childIndex = -1;
        for (int i = 0; i < options.length; i++) {
            if (options[i][0].equals(position))
                childIndex = i;
        }

        StoryTreeNode removedNode;
        switch (childIndex) {
            case 0:
                removedNode = cursor.getLeftChild();
                if (cursor.getMiddleChild() != null)
                    cursor.getMiddleChild().shift(cursor.getMiddleChild().getDepth());
                cursor.setLeftChild(cursor.getMiddleChild());
                if (cursor.getRightChild() != null)
                    cursor.getRightChild().shift(cursor.getRightChild().getDepth());
                cursor.setMiddleChild(cursor.getRightChild());
                cursor.setRightChild(null);
                break;
            case 1:
                removedNode = cursor.getMiddleChild();
                if (cursor.getRightChild() != null)
                    cursor.getRightChild().shift(cursor.getRightChild().getDepth());
                cursor.setMiddleChild(cursor.getRightChild());
                cursor.setRightChild(null);
                break;
            case 2:
                removedNode = cursor.getRightChild();
                cursor.setRightChild(null);
                break;
            default:
                throw new NodeNotPresentException();
        }

        return removedNode;
    }
}
