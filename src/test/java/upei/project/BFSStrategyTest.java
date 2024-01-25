package upei.project;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BFSStrategyTest {
    /**
 * Tests for when double jump is required for placing a fence
*/
    @Test
    public void testConstructor_WhenOppNeedsToJumpP() {
        Player p = new Player(new int[]{6,8}, "P1", 0);
        Player opp = new Player(new int[]{8,8}, "P2", 16);
        Board board = new Board(p,opp);
        Fence fence1 = new Fence(new String[]{"1","6,7"});
        Fence fence2 = new Fence(new String[]{"1","6,9"});
        Fence fence3 = new Fence(new String[]{"0","9,8"});
        //Fence fence4 = new Fence(new String[]{"0","5,6"});
        board.placeFence(fence1);
        board.placeFence(fence2);
        board.placeFence(fence3);
        //board.placeFence(fence4);
        new BFSStrategy(board, 0);
        assertTrue(BFSStrategy.findPath(board) != null);
    }

    /**
 * Tests for when only option is a diagnol for placing fence
*/
    @Test
    public void testConstructor_WhenOppNeedsToDiagP() {
        Player p = new Player(new int[]{6,8}, "P1", 0);
        Player opp = new Player(new int[]{8,8}, "P2", 16);
        Board board = new Board(p,opp);
        Fence fence1 = new Fence(new String[]{"1","6,7"});
        Fence fence2 = new Fence(new String[]{"0","5,6"});
        Fence fence3 = new Fence(new String[]{"0","9,6"});
        Fence fence4 = new Fence(new String[]{"1","8,9"});
        //Fence fence4 = new Fence(new String[]{"0","5,6"});
        board.placeFence(fence1);
        board.placeFence(fence2);
        board.placeFence(fence3);
        board.placeFence(fence4);
        //board.placeFence(fence4);
        new BFSStrategy(board, 0);
        assertTrue(BFSStrategy.findPath(board) != null);
    }
    
    /**
 * Tests for when there ar fences blocking a whole row to goal
*/
    @Test
    public void testConstructor_NoPathToGoal() {
        Player p = new Player(new int[]{6,8}, "P1", 0);
        Player opp = new Player(new int[]{8,8}, "P2", 16);
        Board board = new Board(p,opp);
        Fence fence1 = new Fence(new String[]{"0","3,0"});
        Fence fence2 = new Fence(new String[]{"0","3,4"});
        Fence fence3 = new Fence(new String[]{"0","3,8"});
        Fence fence4 = new Fence(new String[]{"0","3,12"});
        Fence fence5 = new Fence(new String[]{"0","5,14"});
        Fence fence6 = new Fence(new String[]{"1","4,13"});
        //Fence fence4 = new Fence(new String[]{"0","5,6"});
        board.placeFence(fence1);
        board.placeFence(fence2);
        board.placeFence(fence3);
        board.placeFence(fence4);
        board.placeFence(fence5);
        board.placeFence(fence6);

        //new BFSStrategy(board, 0);
        assertTrue(BFSStrategy.findPath(board) == null);
    }
}