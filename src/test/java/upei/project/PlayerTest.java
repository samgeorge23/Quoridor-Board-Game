package upei.project;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    /**
     * Testing if the player class constructor is creating the player object properly
     */
    @Test
    public void testConstructorOne(){
        Player player = new Player(new int[]{2,4},"P1",0);
        assertEquals("P1",player.getName(),"The player object failed for loading the correct name.");
        assertArrayEquals(new int[]{2,4},player.getCoords(),"The player object failed to load the correct coordinates provided in the constructor.");
        assertEquals(0,player.getGoalRow(),"The player object failed to load the appropriate goal row given in the constructor.");
    }


    /**
     * Testing if the players class second constructor is creating the player object properly
     */
    @Test
    public void testConstructorTwo(){
        Player player = new Player(new int[]{0,4},"P2",8);
        Player duplicatePlayer = new Player(player);
        assertEquals("P2",duplicatePlayer.getName(),"The player object failed for loading the correct name.");
        assertArrayEquals(new int[]{0,4},duplicatePlayer.getCoords(),"The player object failed to load the correct coordinates provided in the constructor.");
        assertEquals(8,duplicatePlayer.getGoalRow(),"The player object failed to load the appropriate goal row given in the constructor.");
    }

    /**
     * test if player position moves to its new expected position on the board
     */
    @Test
    public void testMoveTo(){
        int[] initalCoord = {2,4};
        int[] finalCoord = {2,5};
        Player player = new Player(initalCoord,"P1",0);
        player.moveTo(finalCoord);
        assertArrayEquals(finalCoord,player.getCoords(),"The player object failed to move from "+Arrays.toString(initalCoord)+" to "+Arrays.toString(finalCoord));
    }

    /**
     * test if player can use it's available fences after placing a fence
     */
    @Test
    public void testPlaceFence_WhenThereAreFencesLeft(){
        Player player = new Player(new int[]{16,8},"P1",0);
        assertTrue(player.placeFence(),"The player object failed to place the fence.");
    }

    /**
     * test the player's total number of available fences  after placing the first fence
     */
    @Test
    public void testPlaceFence_WhenFenceIsPlaced(){
        Player player = new Player(new int[]{16,8},"P1",0);
        player.placeFence();
        assertEquals(9,player.getFencesLeft(),"Failed to deduct the the fence that has been used.");
    }

    /**
     * test if two different player objects are equal to each other
     */
    @Test
    public void testEquals_DifferentPlayerObjectsEqualsToEachOther(){
        Player p1 = new Player(new int[]{16,8},"P1",0);
        Player p2 = new Player(new int[]{0,8},"P2",16);
        boolean check = p1.equals(p2);
        assertFalse(check,"Both the player object should not match as they are not the same objects.");
    }

    /**
     * test the appropriate outcome when checking a player object is equal to null
     */
    @Test
    public void testEquals_PlayerObjectsEqualsToNull(){
        Player p1 = new Player(new int[]{16,8},"P1",0);
        Player p2 = null;
        boolean check = p1.equals(p2);
        assertFalse(check,"Both the player object should not match as they are not the same initialized objects.");
    }

    /**
     * test if two different player objects are equal if their coordinates are the same
     */
    @Test
    public void testEquals_BothPlayerCoordinatesAreEqual(){
        Player p1 = new Player(new int[]{12,8},"P1",0);
        Player p2 = new Player(new int[]{10,4},"P1",16);
        boolean check = p1.equals(p2);
        assertTrue(check, "Both the players should be equal due to same name.");
    }
}
