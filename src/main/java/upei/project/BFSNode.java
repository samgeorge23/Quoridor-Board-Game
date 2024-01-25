package upei.project;

/**
 * This node is used to implement the breadth first search algorithm
 * Each node has a parent node, a state represented as player coordinates,
 * and x, y coordinates.
 */
public class BFSNode{
    /**
     * The parent node of current node
     */
    protected BFSNode parent;
    /**
     * The player coordinates of current node
     */
    protected int[] state;
    /**
     * The y coordinate of the player
     */
    protected int y;
    /**
     * The x coordinate of the player
     */
    protected int x;


    /**
     * Constructs a new BFSNode with the specified parent and state
     * @param parent The previous node
     * @param state  The player coordinates of the previous node
     */
    protected BFSNode(BFSNode parent, int[] state) {
        this.parent = parent;
        this.state = state;
        this.x = state[1];
        this.y = state[0];
    }
}
