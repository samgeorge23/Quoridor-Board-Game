package upei.project;

import java.util.*;

/**
 * The board creates the platform where the player and the fences can interact with each other
 */
class Board {
    /**
     * It contains the list of already placed fences on the board
     */
    private ArrayList<Fence> fences = new ArrayList<>();
    /**
     * The length of the square board
     */
    private final static int SIZE = 17;
    /**
     * The square board for the players to interact with
     */
    private static int[][] board = new int[SIZE][SIZE];
    /**
     * The player of the board
     */
    private Player _p;
    /**
     * the opponent of the board
     */
    private Player _opp;
    /**
     * the current player of board
     */
    private Player currentPlayer;
    /**
     * the current opponent of board
     */
    private Player currentOpp;
    /**
     * Shows if the updateBoard method updated the board successfully
     */
    protected boolean succesfullUpdate;

    /**
     * The board is created when both the players are taken as parameters
     * @param p Player
     * @param opp Opponent player
     */
    protected Board(Player p, Player opp) {
        this._p = p;
        this._opp = opp;
        this.currentPlayer = _p;
        this.currentOpp = _opp;
    }

    /**
     * The board is created when attributes are taken from another board
     * @param b the other board object
     */
    protected Board(Board b) {
        this.fences = new ArrayList<>(b.getFences());
        this._p = new Player(b.getP());
        this._opp = new Player(b.getOpp());
        this.currentPlayer = new Player(b.getCurrentPlayer());
        this.currentOpp = new Player(b.getCurrentOpp());
    }

    
    /** 
     * @return Player
     */
    protected Player getP() {
        return this._p;
    }

    protected Player getOpp() {
        return this._opp;
    }

    protected Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    protected Player getCurrentOpp() {
        return this.currentOpp;
    }

    /**
     * This method makes opponent the current player and the current player to the opponent to cause both players
     * to perform accordingly during their turn
     */
    protected void switchPlayers() {
        if(this.getCurrentPlayer().equals(this.getP())) {
            this.currentPlayer = this.getOpp();
            this.currentOpp = this.getP();
        } else{
            this.currentPlayer = this.getP();
            this.currentOpp = this.getOpp();
        }
    }

    /**
     * Place the created fence with the appropriate position on the board
     * @param f A created fence
     */
    protected void placeFence(Fence f) {
        this.fences.add(f);
    }

    protected void removeFence() {
        this.fences.remove(this.fences.size()-1);
    }
    
    protected ArrayList<Fence> getFences() {
        return new ArrayList<>(this.fences);
    }

    //bool check to see if player has reached the goal

    /**
     * This method checks if the player or opponent has reached the opposite end of the board to win the game
     * @return true if the player reached the goal
     */
    protected boolean isOver() {
        if(this.getCurrentPlayer().getGoalRow() == this.getCurrentPlayer().getY() || this.getCurrentOpp().getGoalRow() == this.getCurrentOpp().getY()) {
            return true;
        }
        return false;
    }


    /**
     * Displays the contents and state of the players on the board
     */
    protected void display_board(){
        int[] p1Coord = this.getP().getCoords();
        int[] p2Coord = this.getOpp().getCoords();
        int[] index;
        ArrayList<Fence> fences = this.getFences();
        int hasFence = -1;

        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                index = new int[]{i, j};
                //check if theres a fence on left or below
                for(Fence f : fences) {
                    if(f.isVert()) {
                        //if top left corner matches or top right mathes
                        if(Arrays.equals(f.getCoords()[0], index) || Arrays.equals(index, new int[] {f.getCoords()[0][0]+1, f.getCoords()[0][1]}) || Arrays.equals(index, new int[] {f.getCoords()[0][0]+2, f.getCoords()[0][1]})) {
                            hasFence = 1;
                        }
                    } else {
                        //if top left corner match or bottom left matches
                        if(Arrays.equals(f.getCoords()[0], index) || Arrays.equals(index, new int[] {f.getCoords()[0][0], f.getCoords()[0][1]+1}) || Arrays.equals(index, new int[] {f.getCoords()[0][0], f.getCoords()[0][1]+2})) {
                            hasFence = 0;
                        }
                    }
                }

                //if no horz fence

                if(Arrays.equals(index, p1Coord)){
                    System.out.printf("1 ");
                }
                else if(Arrays.equals(index, p2Coord)){
                    System.out.printf("2 ");
                }
                else if(hasFence==1){
                    System.out.printf("█ ");
                }
                else if(hasFence==0){
                    System.out.printf("█ ");
                }
                else if(index[0]%2!=0 || index[1]%2!=0){
                    System.out.printf("  ");
                }
                else{
                    System.out.printf("0 ");
                }
                hasFence=-1;
            }
        System.out.println("");
        }
    }


    /**
     * Updates the players action of moving or placing the fence on the board
     * @param placingFence the player is placing the fence or moving
     * @param sGoal the player movement input
     * @param data the fence placement input
     * @return the updated board content
     */
    protected Board updateBoard(boolean placingFence, String sGoal, String[] data) {
        Player p = this.getCurrentPlayer();
        
        if(placingFence) {
            //create, validate, add new fence else return false
            Fence f = new Fence(data);
            if(f.isValid(this) && p.placeFence()) {
                this.placeFence(f);
                this.switchPlayers(); this.succesfullUpdate = true;
                return this;
            } this.succesfullUpdate = false;

        } else{
            int[] goal = getGoal(sGoal);
            if(isValid(goal, this)){
                p.moveTo(goal);
                this.switchPlayers(); this.succesfullUpdate = true;
                return this;
            } this.succesfullUpdate = false;
        }
        return this;
    }

    protected boolean getSuccessfullUpdate(){
        return succesfullUpdate;
    }


    /**
     * This method runs through all the scenerios of the players expected new position and checks if the move is valid
     * @param goal the expected new position of the player
     * @param board the current state of the board
     * @return true if the new position is valid  for the player to move
     */
    protected static boolean isValid(int[] goal, Board board) {
        //organize data
        Player p = board.getCurrentPlayer();
        Player opp = board.getCurrentOpp();
        int[] pCoord = p.getCoords();
        int[] oppCoord = opp.getCoords();

        //check silly moves
        if(Arrays.equals(goal, null) || Arrays.equals(goal, oppCoord) || Arrays.equals(goal, pCoord) || outOfBounds(goal)) {
            return false;
        }

        if(fenceBlocksMove(p, goal, board.getFences())) {
            return false;
        }

        //opp neighbours player, validate special case moves (jump/diagnol and not blocked by fence return true)
        if(oppNeighbours(board)!=0) {
            //check if goal is jumping opp
            if(pJumpsOpp(goal, board) == 1) {
                //p is jumping opp, no fence in the way
                return true;
            } else if(pJumpsOpp(goal, board) == -1) {
                //p is not jumping, and there is a wall in the way of jump, check if move is diagonal and that there isnt a fence blocking
                if(pDiagsOpp(goal, board) && !fenceBlocksMove(opp, goal, board.getFences())) {
                    return true;
                }
            }


        }
        
        //validate standard moves (up/down/left/right and not blocked by fence return true)
        //if move is up or down:
        if((goal[0] == pCoord[0]+2 || goal[0] == pCoord[0]-2) && (goal[1] == pCoord[1])) {
            if(!fenceBlocksMove(p, goal, board.getFences())) {
                return true;
            } 
        }
        
        //if move is right or left:
        else if((goal[1] == pCoord[1]+2 || goal[1] == pCoord[1]-2) && (goal[0] == pCoord[0])) {
            if(!fenceBlocksMove(p, goal, board.getFences())) {
                return true;
            } 
        }

        //all possible moves have been checked, this is illegal
        return false; 
    }


    /**
     * This method checks if any of the existing fences are blocking the player from advancing
     * @param p the player
     * @param goal the player's expected new position
     * @param fences the list of the existing fences
     * @return true if the fence blocks the player's new move
     */
    private static boolean fenceBlocksMove(Player p, int[] goal, ArrayList<Fence> fences) {
        int y = p.getY(); int x = p.getX(); int gY = goal[0]; int gX = goal[1]; int i;
        // double check 4 spaces between goal and player to prevent player going past a potential fence in its way.
        if(gY+4==y || gY-4==y || gX+4==x || gX-4==x){
            i = 3;
        } else {
            i = 1;
        }

        for(Fence f : fences) {
            int[] uL = f.getCoords()[0];
            int[] bR = f.getCoords()[1];

            //if were making a move that might collide with the fence
            //moving horz and fence is vert                                         or moving vert and fence is horz
            if((y == gY && f.isVert() && (uL[0] == gY || bR[0] == gY)) || (x == gX && !f.isVert() && (uL[1] == gX || bR[1] == gX))) {
                //if either the goal or player lands in the upper left or lower right coord of the fence return true, its blocking
                //vert fence checking
                //problem starts here
                if( (uL[0]+i == gY && uL[0]-1 == y) || (uL[0]-i == gY && uL[0]+1 == y) || ( bR[0]+i == gY && bR[0]-1 == y) || ( bR[0]-i == gY && bR[0]+1 == y) ) {
                    return true;
                }
                //horz fence checking
                else if((uL[1]+i == gX && uL[1]-1 == x ) || (uL[1]-i == gX && uL[1]+1 == x ) || ( bR[1]+i == gX && bR[1]-1 == x) || (bR[1]-i == gX && bR[1]+1 == x)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * This method checks if the player can move diagonally when there is the opponent in front of the player and
     * the fence behind the opponent
     * @param goal player's new expected position
     * @param board the current state of the board
     * @return true if the player can move diagonally under this condition
     */
    private static boolean pDiagsOpp(int[] goal, Board board) {
        Player opp = board.getCurrentOpp();

        int gY = goal[0]; int gX = goal[1]; int oppX = opp.getX(); int oppY = opp.getY();
        int neighbour = oppNeighbours(board);
        if(neighbour==1 || neighbour==2) {
            //if its up or down is goal to right?                 or left of opp?
            if(oppX+2 ==gX && oppY == gY || oppX-2 ==gX && oppY == gY){
                return true;
            } else return false;
        } else if(neighbour==3 || neighbour ==4) {
            //if its left or right is goal to up?                 or down of opp?
            if(oppY+2 == gY && oppX ==gX || oppY-2 == gY && oppX ==gX){
                return true;
            } else return false;
        }
        //not neighbouring
        return false;
    }



    /**
     * This method checks if opponent is infront of the direction of the player so that it can jump over it.
     * @param goal player's new expected position
     * @param board the current state of the board
     * @return 0 for no, 1 for yes, -1 for double blocked by a wall
     */
    private static int pJumpsOpp(int[] goal, Board board) {
        Player opp = board.getCurrentOpp(); 
        int gY = goal[0]; int gX = goal[1]; int oppX = opp.getX(); int oppY = opp.getY();
        int neighbour = oppNeighbours(board);
        if(neighbour==1) {
            //if opp is up:
            //if fence blocks the double jump:
            if(fenceBlocksMove(opp, new int[]{oppY-2, oppX}, board.getFences())) {
                return -1;
            } 
            //if fence doesnt block double jump, and we are making double jump
            else if(oppY-2 == gY && oppX ==gX){
                return 1;
            }
            
        } else if(neighbour==2) {
            //if its down:
            if(fenceBlocksMove(opp, new int[]{oppY+2, oppX}, board.getFences())) {
                return -1;
            } else if(oppY+2 == gY && oppX ==gX){
                return 1;
            } 
        } else if(neighbour==3) {
            //if its left
            if(fenceBlocksMove(opp, new int[]{oppY, oppX-2}, board.getFences())) {
                return -1;
            } else if(oppX-2 ==gX && oppY == gY){
                return 1;
            } 
        } else if(neighbour==4) {
            //if its right
            if(fenceBlocksMove(opp, new int[]{oppY, oppX+2}, board.getFences())) {
                return -1;
            } else if(oppX+2 ==gX && oppY == gY){
                return 1;
            } 
        }
        //not neighbouring
        return 0;
    }

    /*opp blocks : return 
    *
    */

    /**
     * This method checks if the player has the opponent in front of it in horizontal or vertical direction
     * @param board the current state of the board
     * @return when no opponent close by:0, when opponent up direction:1, down direction:2, left direction:3,
     * right direction:4
     */
    private static int oppNeighbours(Board board) {
        Player p = board.getCurrentPlayer();
        Player opp = board.getCurrentOpp();
        int pX = p.getX(); int pY = p.getY(); int oppX = opp.getX(); int oppY = opp.getY();
        //up
        if(oppY == pY-2) {
            return 1;
        } 
        //down
        else if(oppY == pY+2) {
            return 2;
        } 
        //right
        else if(oppX == pX+2) {
            return 4;
        } 
        //left
        else if(oppX == pX-2) {
            return 3;
        }
        return 0;
    }

    /**
     * This method checks if the move is within the board and the move is within the player reserved spaces
     * @param coord The player's new expected position
     * @return true if the player's new position is out of the board
     */
    private static boolean outOfBounds(int[] coord) {
        if(coord[0] > 16 || coord[0] < 0 || coord[1] > 16 || coord[1] < 0) {
            if(coord[0]%2==0 && coord[1]%2==0){
                return true;
            }
        } return false;
    }

    /**
     * This method processes the new expected position of the player into readable form for the board class to use
     * @param sGoal the new position data that needs to be processed
     * @return the processed new position data of the player
     */
    private int[] getGoal(String sGoal) {
        int[] goal = this.getCurrentPlayer().getCoords();
        if(sGoal.contains(",")) {
            //organize data
            String[] pair = sGoal.split(",");
            //put in int array
            try {
                goal[0] = Integer.parseInt(pair[0]);
                goal[1] = Integer.parseInt(pair[1]);
            } catch(Exception E) {
                //bad goal input, handled later
                return null;
            }
        }
        return goal;
    }

    //returns int[][] board
    protected static int[][] getBoard() {
        return board;
    }

    protected static int getSize() {
        return SIZE;
    }

    @Override
    public String toString() {
        return  "Current Player: " + this.getCurrentPlayer().toString() + "\n" +
                "Current Oppoment: " + this.getCurrentOpp().toString() + "\n" +
                "Current Fences Placed: " + this.getFences().size() + "\n" +
                "Last Update Status: " + this.succesfullUpdate;
    }
}