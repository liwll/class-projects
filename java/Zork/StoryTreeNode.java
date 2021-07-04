/**
 * This class represents a node in a StoryTree, containing information about
 * the position, option, message, and children.
 *
 * @author
 * liwll
 */
public class StoryTreeNode {
    public static final String WIN_MESSAGE = "YOU WIN";
    public static final String LOSE_MESSAGE = "YOU LOSE";
    private String position;
    private String option;
    private String message;
    private StoryTreeNode leftChild;
    private StoryTreeNode middleChild;
    private StoryTreeNode rightChild;
    private int depth;

    /**
     * Default constructor for a StoryTreeNode
     */
    public StoryTreeNode() {
        position = "";
        option = "";
        message = "";
        depth = 0;
    }

    /**
     * Constructor for a StoryTreeNode which accepts parameters for the
     * position, option, and message
     * @param position
     *  A String which represents the node's position
     * @param option
     *  A String which represents the node's option
     * @param message
     *  A String which represents the node's message
     */
    public StoryTreeNode(String position, String option, String message) {
        this.position = position;
        this.option = option;
        this.message = message;
        depth = position.length() / 2;
    }

    /**
     * Getter method for the position of the node
     * @return
     *  A String which represents the node's position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Getter method for the option of the node
     * @return
     *  A String which represents the node's option
     */
    public String getOption() {
        return option;
    }

    /**
     * Getter method for the message of the node
     * @return
     *  A String which represents the node's message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Getter method for the leftChild of the node
     * @return
     *  A StoryTreeNode which is the leftChild of the node
     */
    public StoryTreeNode getLeftChild() {
        return leftChild;
    }

    /**
     * Getter method for the middleChild of the node
     * @return
     *  A StoryTreeNode which is the middleChild of the node
     */
    public StoryTreeNode getMiddleChild() {
        return middleChild;
    }

    /**
     * Getter method for the rightChild of the node
     * @return
     *  A StoryTreeNode which is the rightChild of the node
     */
    public StoryTreeNode getRightChild() {
        return rightChild;
    }

    /**
     * Getter method for a custom field, the depth of the node
     * @return
     *  An int value that is the depth of the node
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Setter method for the position of the node
     * @param position
     *  The new position of the node
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Setter method for the option of the node
     * @param option
     *  The new option of the node
     */
    public void setOption(String option) {
        this.option = option;
    }

    /**
     * Setter method for the message of the node
     * @param message
     *  The new message of the node
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Setter method for the leftChild of the node
     * @param leftChild
     *  The new leftChild of the node
     */
    public void setLeftChild(StoryTreeNode leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * Setter method for the middleChild of the node
     * @param middleChild
     *  The new middleChild of the node
     */
    public void setMiddleChild(StoryTreeNode middleChild) {
        this.middleChild = middleChild;
    }

    /**
     * Setter method for the rightChild of the node
     * @param rightChild
     *  The new rightChild of the node
     */
    public void setRightChild(StoryTreeNode rightChild) {
        this.rightChild = rightChild;
    }

    /**
     * Method which determines if the node is a leaf
     * @return
     *  Returns true if the node is a leaf, false if not
     */
    public boolean isLeaf() {
        return leftChild == null;
    }

    /**
     * Method which determines if the node is a winning node
     * @return
     *  True if it is a leaf and contains the winning message, false if not
     */
    public boolean isWinningNode() {
        return (isLeaf() && message.contains(WIN_MESSAGE));
    }

    /**
     * Method which determines if the node is a losing node
     * @return
     *  True if it is a leaf and contains the losing message, false if not
     */
    public boolean isLosingNode() {
        return (isLeaf() && message.contains(LOSE_MESSAGE));
    }

    /**
     * Recursive helper method for the removeChild method in StoryTree
     * @param shiftDepth
     *  The depth of the node that is to be shifted
     * @return
     *  A String which represents the shifted position of the node
     */
    public String shift(int shiftDepth) {
        if (isLeaf()) {
            int charIndex = shiftDepth * 2;
            int newChar = Integer.parseInt(""
                    + position.charAt(charIndex)) - 1;
            position = position.substring(0, charIndex)
                    + newChar + position.substring(charIndex + 1);
            return position;
        }
        else {
            if (middleChild != null) {
                middleChild.shift(shiftDepth);
                if (rightChild != null)
                    rightChild.shift(shiftDepth);
            }
            String shiftedPos = getLeftChild().shift(shiftDepth);
            return position = shiftedPos.substring(0, shiftedPos.length() - 2);
        }
    }
}
