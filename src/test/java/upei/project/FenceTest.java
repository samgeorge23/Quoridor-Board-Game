package upei.project;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class FenceTest {

    /**
     * test how the fence class behaves when horizontal fence coordinates are passed
     */
    @Test
    public void testConstructor_WhenNormalHorizontalCoordinatesIsPassed(){
        String[] data = {"0","15,2"};
        Fence fence = new Fence(data);
        assertFalse(fence.isVert());
        assertArrayEquals(new int[]{15,2},fence.getCoords()[0]);
    }

    /**
     * test how the fence class behaves when vertical fence coordinates are passed
     */
    @Test
    public void testConstructor_WhenNormalVerticalCoordinatesIsPassed(){
        String[] data = {"1","10,3"};
        Fence fence = new Fence(data);
        assertTrue(fence.isVert());
        assertArrayEquals(new int[]{10,3},fence.getCoords()[0]);
    }

    /**
     * test how the fence class behaves when wrong coordinates for the fence is passed
     */
    @Test
    public void testConstructor_WhenBadFenceCoordinatesIsPassed(){
        String[] data = {"1","15,3"};
        Fence fence = new Fence(data);
        assertFalse(fence.isValidFence());
    }

    /**
     * the fence class returns false as it is an invalid fence when null coordinates are passed
     */
    @Test
    public void testConstructor_WhenNullCoordinatesIsPassed(){
        String[] data = {"1",null};
        Fence fence = new Fence(data);
        assertFalse(fence.isValidFence());
    }

    /**
     * test that the fence is not valid when out of bounds coordinates are passed
     */
    @Test
    public void testConstructor_WhenOutOfBoundsCoordinatesIsPassed(){
        String[] data = {"1","17,3"};
        Fence fence = new Fence(data);
        assertFalse(fence.isValidFence());
    }

    /**
     * test when the fence is passed with null orientation it is an invalid fence as the fence really needs an orientation on the board
     */
    @Test
    public void testConstructor_WhenNullPositionIsPassed(){
        String[] data = {null,"10,3"};
        Fence fence = new Fence(data);
        assertFalse(fence.isValidFence());
    }

    /**
     * test when fence class receives null data and return false as the created fence is invalid
     */
    @Test
    public void testConstructor_WhenNullIsPassed(){
        String[] data = {null,null};
        Fence fence = new Fence(data);
        assertFalse(fence.isValidFence());
    }

    /**
     * test whether the fence is valid when a fence adhering to the rules of the game is created
     */
    @Test
    public void testIsValid_NormalFenceIsPlaced(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        Fence fence = new Fence(new String[]{"0","11,4"});
        board.placeFence(fence);
        Fence fence1 = new Fence(new String[]{"0","9,4"});
        assertTrue(fence1.isValid(board));
    }

    /**
     * test the created fence when placed not on fence reserved places on the board
     */
    @Test
    public void testIsValid_WhenNotOnFenceReservedSpaces(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        Fence fence = new Fence(new String[]{"0","13,3"});
        assertFalse(fence.isValid(board));
    }

    /**
     * test that the upper left coordinate of the created fence is not adjacent to player reserved spaces
     */
    @Test
    public void testIsValid_WhenFencePlacedUpperLeftCoordinateIsNotAdjacentToPlayerReservedSpaces(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        Fence fence = new Fence(new String[]{"0","14,2"});
        assertFalse(fence.isValid(board));
    }

    /**
     * test and ensure that the fence spans two player reserved cells on the board
     */
    @Test
    public void testIsValid_WhenFenceHasWrongSize(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        Fence fence = new Fence(new String[]{"0","15,2,15,5"});
        assertFalse(fence.isValid(board));
    }

    /**
     * test whether the created fence overlaps other exiting fences placed on the board
     */
    @Test
    public void testIsValid_IfFencesOverlaps(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        Fence fence1 = new Fence(new String[]{"0","13,4,13,6"});
        board.placeFence(fence1);
        Fence fence2 = new Fence(new String[]{"0","13,6,13,8"});
        assertFalse(fence2.isValid(board));
    }

    /**
     * test whether the created fence is valid to be placed and does not completely block the pathway of the player to the goal
     */
    @Test
    public void testIsValid_IfFenceCompletelyClosesPLayerPathwayToGoal(){
        Player p = new Player(new int[]{16,8}, "P1", 0);
        Player opp = new Player(new int[]{0,8}, "P2", 16);
        Board board = new Board(p,opp);
        int num = 4;
        int start = 0;
        for(int i=0;i<4;i++){
            Fence fence = new Fence(new String[]{"0",("11,"+start)});
            board.placeFence(fence);
            start += num;
        }
        Fence fence2 = new Fence(new String[]{"0","13,14"});
        board.placeFence(fence2);
        Fence fence3 = new Fence(new String[]{"1","12,13"});
        assertFalse(fence3.isValid(board));
    }

}
