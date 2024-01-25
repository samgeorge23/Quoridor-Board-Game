package upei.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    /**
     * checks constructors set values as they should
     */
    @Test
    public void testConstructor1(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        assertEquals("P1",board.getP().getName());
        assertEquals("P2",board.getOpp().getName());
        assertArrayEquals(new int[]{16,8},board.getP().getCoords());
        assertArrayEquals(new int[]{0,8},board.getOpp().getCoords());
        assertEquals(0,board.getP().getGoalRow());
        assertEquals(16,board.getOpp().getGoalRow());
    }

    /** 
     * Checks constructors set values as they should
     */
    @Test
    public void testConstructor2(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        Board board1 = new Board(board);
        assertEquals("P1",board1.getP().getName());
        assertEquals("P2",board1.getOpp().getName());
        assertArrayEquals(new int[]{16,8},board1.getP().getCoords());
        assertArrayEquals(new int[]{0,8},board1.getOpp().getCoords());
        assertEquals(0,board1.getP().getGoalRow());
        assertEquals(16,board1.getOpp().getGoalRow());
    }

    /**
     * Checks to see if isOver returns as expected when goal state is true for one of the players
     */
    @Test
    public void testIsOver_WhenReachesTheGoal(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        board.getP().moveTo(new int[]{0,6});
        assertTrue(board.isOver());
    }

    /**
     * Checks to see if isOver returns as expected when a goal state is not reached for one of the players
     */
    @Test
    public void testIsOver_WhenDidNotReachTheGoal(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        board.getP().moveTo(new int[]{14,8});
        assertFalse(board.isOver());
    }

    /**
     * Checks to see if a fence is placed and saved succcesfully when one is placed into the board
     */
    @Test
    public void testUpdateBoard_WhenFenceIsPlaced(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        board.updateBoard(true,"fence",new String[]{"0","5,8"});
        Fence fence = board.getFences().get(0);
        assertFalse(fence.isVert());
        assertArrayEquals(new int[]{5,8},fence.getCoords()[0]);

    }

    //The validity of the fence was confirmed through performing test cases on it in the fence class.
    /**
     * If the fence is placed with bad coordinates, dont change anything, dont set succesful move, dont switch players
     */
    @Test
    public void testUpdateBoard_WhenFenceIsPlacedWithNotValidCoordinates(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        board.updateBoard(true,"fence",new String[]{"1","17,8"});
        assertFalse(board.getSuccessfullUpdate());
    }

    /**
     * Checks if we can place a fence when placingFeence === false
     */
    @Test
    public void testUpdateBoard_WhenPlacingFenceParameterAsFalse(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        board.updateBoard(false,"fence",new String[]{"0","5,8"});
        assertFalse(board.getSuccessfullUpdate());
    }

    /**
     * checks regualr for valid moves 
     */
    @Test
    public void testUpdateBoard_WhenPlayerNormallyIsMoving(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        board.updateBoard(false,"14,8",new String[2]);
        int[] playerCoords = p.getCoords();
        assertArrayEquals(new int[]{14,8},playerCoords);
    }

    /**
     * Checks out of movement range movements
     */
    @Test
    public void testUpdateBoard_WhenPlayerNormallyIsMovingToNotValidCoordinates(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        board.updateBoard(false,"13,8",new String[2]);
        assertFalse(board.getSuccessfullUpdate());
    }

    /**
     * Checks if there is a movement when placing fence is true (should reurn false as empty data)
     */
    @Test
    public void testUpdateBoard_WhenPlayerNormallyIsMovingButPlacingFenceIsTrue(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        board.updateBoard(true,"14,8",new String[2]);
        assertFalse(board.getSuccessfullUpdate());
    }

    /**
     * Checks player can not move out of bounds
     */
    @Test
    public void testUpdateBoard_WhenPlayerMovesOutOfBounds(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        board.updateBoard(true,"20,8",new String[2]);
        assertFalse(board.getSuccessfullUpdate());
    }

    /**
     * Checks player can jump diagonlly when appropriate
     */
    @Test
    public void testUpdateBoard_WhenPlayerMovesDiagonally(){
        Player opp = new Player(new int[]{8,8}, "P2", 0);
        Player p = new Player(new int[]{6,8}, "P1", 16);
        Board board = new Board(p,opp);
        Fence fence2 = new Fence(new String[] {"1","6,9"});
        Fence fence3 = new Fence(new String[] {"0","5,6"});
        board.placeFence(fence2);
        board.placeFence(fence3);
        
        board.updateBoard(true,"6,6",new String[2]);
        assertFalse(board.getSuccessfullUpdate());
    }

    /**
     * Checks player cant double jump when wall is behind opp
     */
    @Test
    public void testUpdateBoard_WhenPlayerIsBlockedByWallForDJump(){
        Player opp = new Player(new int[]{8,8}, "P2", 0);
        Player p = new Player(new int[]{6,8}, "P1", 16);
        Board board = new Board(p,opp);
        Fence fence2 = new Fence(new String[] {"1","6,9"});
        Fence fence3 = new Fence(new String[] {"0","5,6"});
        board.placeFence(fence2);
        board.placeFence(fence3);
        
        board.updateBoard(true,"4,8",new String[2]);
        assertFalse(board.getSuccessfullUpdate());
    }

    /**
     * Checks player can double jump vert
     */
    @Test
    public void testUpdateBoard_WhenPlayerIsDJumpVert(){
        Player opp = new Player(new int[]{8,8}, "P2", 0);
        Player p = new Player(new int[]{6,8}, "P1", 16);
        Board board = new Board(p,opp);
        
        board.updateBoard(true,"4,8",new String[2]);
        assertFalse(board.getSuccessfullUpdate());
    }
}
