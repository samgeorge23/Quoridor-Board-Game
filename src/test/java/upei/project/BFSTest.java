package upei.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BFSTest {
    /**
     * Tests if we can fully enclose the current player
     */
    @Test
    public void testConstructor_WhenNoPathForP() {
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        Fence fence1 = new Fence(new String[]{"1","14,7"});
        Fence fence2 = new Fence(new String[]{"1","14,9"});
        board.placeFence(fence1);
        board.placeFence(fence2);
        Fence fence3 = new Fence(new String[]{"0","13,6"});
        assertFalse(new BFS(board, fence3).solution);
    }

    /**
     * Tests if we can fully enclose the current opp
     */
    @Test
    public void testConstructor_WhenNoPathForOpp() {
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        Fence fence1 = new Fence(new String[]{"1","0,7"});
        Fence fence2 = new Fence(new String[]{"1","0,9"});
        board.placeFence(fence1);
        board.placeFence(fence2);
        Fence fence3 = new Fence(new String[]{"0","3,8"});
        assertFalse(new BFS(board, fence3).solution);
    }

    /**
     * Tests if we can enclose current player on three sides with opp on the not blocked side requiring a jump
     */
    @Test
    public void testConstructor_WhenPNeedsToJumpOpp() {
        Player p = new Player(new int[]{8,8}, "P1", 0);
        Player opp = new Player(new int[]{6,8}, "P2", 16);
        Board board = new Board(p,opp);
        Fence fence1 = new Fence(new String[]{"1","8,7"});
        Fence fence2 = new Fence(new String[]{"1","8,9"});
        board.placeFence(fence1);
        board.placeFence(fence2);
        Fence fence3 = new Fence(new String[]{"0","7,8"});
        assertTrue(new BFS(board, fence3).solution);
    }

    /**
     * Tests if we can enclose current opp on three sides with player on the not blocked side requiring a jump
     */
    @Test
    public void testConstructor_WhenOppNeedsToJumpP() {
        Player p = new Player(new int[]{6,8}, "P1", 0);
        Player opp = new Player(new int[]{8,8}, "P2", 16);
        Board board = new Board(p,opp);
        Fence fence1 = new Fence(new String[]{"1","8,7"});
        Fence fence2 = new Fence(new String[]{"1","8,9"});
        board.placeFence(fence1);
        board.placeFence(fence2);
        Fence fence3 = new Fence(new String[]{"0","7,8"});
        assertTrue(new BFS(board, fence3).solution);
    }
}
