package upei.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
public class RFPTest {
    
    /**
     * Checks to see if any move (fence or movement) selected rnaadomly works succesfully
     */
    @Test
    public void makeValidMove(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        RFP cfs = new RFP(board);
        assertEquals(cfs.board.succesfullUpdate, board.succesfullUpdate);
    }

    /**
     * Checks to see if we can move when there is only a diganol move available
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

        RFP cfs = new RFP(board);
        assertEquals(cfs.board.succesfullUpdate, board.succesfullUpdate);
    }

    /**
     * Checks to see if we can move when only a double jump is available
     */
    @Test
    public void makeMoveWhenDJumpOnlyAvailable(){
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

        RFP cfs = new RFP(board);
        assertEquals(cfs.board.succesfullUpdate, board.succesfullUpdate);
    }
}
