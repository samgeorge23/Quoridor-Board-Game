package upei.project;

import java.util.Arrays;

/**
 * Player is the pawns used in the Quoridor board game to move and interact around the board and reach the opposite end of the board to win the game
 * @author Sam Salu George
 * @author Dale Urquhart
 */

class Player {
    /**
     * The x-coordinate of the player on the board
     */
    private int x;
    /**
     * The y-coordinate of the player on the board
     */
    private int y;
    /**
     * The last row on the board that the play needs to reach to win the game
     */
    private int goalRow;

    /**
     * The x-coordinate and y-coordinate of the player on the board
     */
    private int[] position;
    /**
     * The name of the player to distinguish between the opponent player
     */
    private String name;
    /**
     * The total number of fences that the player has not used yet on the board.
     */
    private int fencesLeft = 10;
    /**
     * The total number of wins of the player.
     */
    private int wins = 0;

    /**
     * This player constructor uses these paramaters to initalize itself on the board
     * @param start the starting coordinates or position of player on the board
     * @param name player identification
     * @param goalRow the row the player has to reach to win
     */
    protected Player(int[] start, String name, int goalRow) {
        this.position = start;
        this.name = name;
        this.goalRow = goalRow;
        this.x = start[1];
        this.y = start[0];
    }

    
    /** 
     * @return int
     */
    protected int getWins() {
        return this.wins;
    }

    /**
     * Stores and updates the total number of wins that the player had
     */
    protected void addWin() {
        this.wins++;
    }

    protected void setName(String n) {
        this.name = n;
    }

    /**
     * This player constructor makes a copy of another player's attributes
     * @param p the Player object
     */
    protected Player(Player p) {
        this.position = p.getCoords();
        this.y = p.getY();
        this.x = p.getX();
        this.name = p.getName();
        this.goalRow = p.getGoalRow();
        this.fencesLeft = p.getFencesLeft();
    }
    
    protected int getFencesLeft() {
        return fencesLeft;
    }

    protected int getY() {
        return this.y;
    }

    protected int getX() {
        return this.x;
    }

    protected int getGoalRow() {
        return this.goalRow;
    }

    /**
     * Moves the player to its next new position on the board
     * @param goal the coordinates that the player needs to arrive
     */
    protected void moveTo(int[] goal) {
        this.position = goal;
        this.x = this.position[1];
        this.y = this.position[0];
    }

    /**
     * Checks if the player has any remaining fences to use
     * @return true if there are anymore fences still left
     */
    protected boolean placeFence() {
        if(fencesLeft > 0) {
            fencesLeft --;
            return true;
        } return false;
    }
    
    protected int[] getCoords() {
        return  Arrays.copyOf(this.position, this.position.length);
    }

    protected String getName() {
        return name;
    }

    /**
     * Checks if the player is equal to another player by the same object or same identification
     * @param obj Any object
     * @return true if both the players objects are the same or same identification
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Player other = (Player) obj;
        return this.getName().equals(other.getName());
    }

    @Override
    public String toString() {
        return "Name: " + this.getName() +", at " + Integer.toString(this.getY()) + "," + Integer.toString(this.getX());
    }
}