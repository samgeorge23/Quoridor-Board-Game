package upei.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

    /**
     * generates and places a horz fence
     */
public class CFPTest {
    @Test
    public void generateHorizontalFenceCoords(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{6,8}, "P2", 16);
        Board board = new Board(p,opp);
        CFP cfs = new CFP(board,0);
        assertArrayEquals(cfs.generateHorizontalFenceCoords(),cfs.generateHorizontalFenceCoords());
    }

    /**
     * Generates and places a vert. fence
     */
    @Test
    public void generateVerticalFenceCoords(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{6,14}, "P2", 16);
        Board board = new Board(p,opp);
        CFP cfs = new CFP(board,0);
        assertArrayEquals(cfs.generateVerticalFenceCoords(),cfs.generateVerticalFenceCoords());
    }

    /**
     * Makes any valid player movement
     */
    @Test
    public void makeValidMove(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{6,8}, "P2", 16);
        Board board = new Board(p,opp);
        CFP cfs = new CFP(board,0);
        assertTrue(board.succesfullUpdate);
    }

    /**
     * Makes a move when none is available, should not go through
     */
    @Test
    public void makeMoveWhenNoneAvailable(){
        Player p = new Player(new int[]{6,8}, "P1", 0);
        Player opp = new Player(new int[]{8,8}, "P2", 16);
        Board board = new Board(p,opp);
        Fence fence1 = new Fence(new String[]{"1","6,7"});
        Fence fence2 = new Fence(new String[]{"0","5,6"});
        Fence fence3 = new Fence(new String[]{"0","9,6"});
        Fence fence4 = new Fence(new String[]{"1","6,9"});
        //Fence fence4 = new Fence(new String[]{"0","5,6"});
        board.placeFence(fence1);
        board.placeFence(fence2);
        board.placeFence(fence3);
        board.placeFence(fence4);

        CFP cfs = new CFP(board,0);
        assertFalse(board.succesfullUpdate);
    }

    /**
     * Makes a move when only diagonal is available
     */
    @Test
    public void makeMoveWhenDiagOnlyAvailable(){
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

        CFP cfs = new CFP(board,0);
        assertEquals(cfs.board.succesfullUpdate, board.succesfullUpdate);
    }

    /**
     * Makes a move when only a double jump is available
     */
    @Test
    public void makeMoveWhenNoneDJumpOnlyAvailable(){
        Player p = new Player(new int[]{6,8}, "P1", 0);
        Player opp = new Player(new int[]{8,8}, "P2", 16);
        Board board = new Board(p,opp);
        Fence fence1 = new Fence(new String[]{"1","6,7"});
        Fence fence2 = new Fence(new String[]{"0","4,6"});
        Fence fence3 = new Fence(new String[]{"0","9,6"});
        Fence fence4 = new Fence(new String[]{"1","6,9"});
        //Fence fence4 = new Fence(new String[]{"0","5,6"});
        board.placeFence(fence1);
        board.placeFence(fence2);
        board.placeFence(fence3);
        board.placeFence(fence4);

        CFP cfs = new CFP(board,0);
        assertTrue(board.succesfullUpdate);
    }
}
