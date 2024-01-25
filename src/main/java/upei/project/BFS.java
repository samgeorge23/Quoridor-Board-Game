package upei.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Searches to check if there is a valid path to a winning state for the current player
 * <p>
 * @author Dale Urquhart et al.
 */
class BFS {
    protected boolean solution;

    /**
 * Constructor checks for a valid path for the current player, switches player and then checks for currentOpp.
 * boolean solution's value represents whether the fence placement willl be valid or not.
 * <p>
 * @param board running instance of the game board
 * @param potentialFence fence that the player is trying to place
 * @return null
 */
    protected BFS(Board board, Fence potentialFence) {
        if(potentialFence!=null){
            boolean s1;
            boolean s2;
            s1 = solve(new Board(board), potentialFence);
            Board check = new Board(board);
            check.switchPlayers();
            s2 = solve(new Board(check), potentialFence);
            this.solution = s1 && s2;
        }
    }   

    /**
 * Places potential fence on board copy, and checks for a path to win.
 * <p>
 * @param board copy of running instance of the game board
 * @param potentialFence fence that the player is trying to place
 * @return true/false for valid placement/invalid placement respectively
 */
    private static boolean solve(Board board, Fence potentialFence) {
        board.placeFence(potentialFence);
        if(findPath(board) == null) {
            return false;
        } else{return true;}
    } 
    
    /**
 * Uses BFS to find a path to winning state and returns the next move from current state for path to win.
 * <p>
 * @param board copy of running instance of the game board
 * @return move/null for valid placement/invalid placement respectively
 */
    protected static int[] findPath(Board board) {
        Player p = board.getCurrentPlayer();
        int goal = p.getGoalRow();
        Queue<BFSNode> frontier = new LinkedList<>();
        boolean[][] explored = new boolean[Board.getSize()][Board.getSize()];
        frontier.add(new BFSNode(null, p.getCoords()));
        BFSNode current;
        int x, y;

        while (frontier.size() != 0) {
            current = frontier.poll();
            x = current.x; y = current.y;
            p.moveTo(current.state);

            if (y == goal) {
                return getMove(current);
            }

            explored[y][x] = true;
            for (int[] state : getValidNeighbours(board, explored)) {
                frontier.offer(new BFSNode(current, state));
                explored[state[0]][state[1]] = true;
            }
        }
        return null; // No path found, root children exausted
    }

    /**
 * Gets all valid children from current state
 * <p>
 * @param board copy of running instance of the game board
 * @param explored boolean Board.SIZExBoard.SIZE array of explored states
 * @return list of potential moves from current player's position
 */
    private static ArrayList<int[]> getValidNeighbours(Board board, boolean[][] explored) {
        ArrayList<int[]> possible = new ArrayList<int[]>();
        int[] check;
        Player p = board.getCurrentPlayer();
        for(int i = -2; i < 3; i++) {
            for(int j = -2; j < 3; j++) {
                check = new int[]{p.getY()+i*2,  p.getX()+j*2};
                if(Board.isValid(check, board) && !explored[check[0]][check[1]] && !possible.contains(check)) {
                    possible.add(check);
                } 
            }
        }
        return possible;
    }

    /**
 * Back tracks to root state through parents and returns the first move for the shortest path to win
 * <p>
 * @param node winning state node
 * @return move to make for shortest path to win
 */
    private static int[] getMove(BFSNode node) {
        List<BFSNode> path = new ArrayList<>();
        while (node.parent != null) {
            path.add(node);
            node = node.parent;
        }
        Collections.reverse(path);

        return path.get(0).state;
    }
}