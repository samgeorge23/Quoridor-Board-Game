package upei.project;

import java.util.Random;

/**
 * Random placement of fence strategy places all the fences randomly across the board or it's player moves
 */
public class RFP {
    boolean wonGame = false;
    int percentageChance = 65;
    Player p;
    Player opp;
    Board board;
    //should be just player and the board
    RFP( Board board){
        this.board = board;
        this.p = board.getCurrentPlayer();
        this.opp = board.getCurrentOpp();
        solve();
        board.switchPlayers();

    }

    
    /** 
     * @return Player
     */
    protected Player getCurrentPlayer(){
        return p;
    }

    /**
     * This method makes the players move to new random valid position
     */
    void move(){
        Random random = new Random();
        String[] commandArray = {"right","left","back"};
        boolean moved = false;
        if(board.updateBoard(false,futureMove("front"),null).succesfullUpdate){
            moved = true;
        }
        int iters=0;
        while(!moved){
            iters++;
            if(iters>100) {
                //System.out.println("LAZY CUNT MOMENT PART 2");
                board.getP().moveTo(new int[]{board.getP().getGoalRow(), 0});
                return;
            }
            int randNum = random.nextInt(3);
            if(board.updateBoard(false,futureMove(commandArray[randNum]),null).succesfullUpdate){
                moved = true;
            }
        }

    }

    /**
     * This method decides and performs the action whether the player should place fence randomly or move
     * @return true if the player reaches the end of the board
     */
    protected boolean solve(){
        boolean gameWon = false;
        if(getCurrentPlayer().getFencesLeft()==0){
            percentageChance =100;
        }
        if(RFP.movementOrFenceChoice(percentageChance)){
            move();
            //System.out.println("Current Player Chance: "+getCurrentPlayer().getName()+" moved");
        }
        else{
            addRandomFence();
            //System.out.println("Current Player Chance: "+getCurrentPlayer().getName()+" placed fence");
        }

        if(board.isOver()){
            wonGame = true;
            //System.out.printf("%s has won the game!!!!\n",board.getCurrentPlayer().getName());
        }
        return gameWon;
    }

    /**
     * This method generates the next new position of the player int the specific direction
     * @param command the direction of the player movement
     * @return the predicted new player's position
     */
    private String futureMove(String command){
        String pName = board.getP().getName();
        int y = p.getY();
        int x = p.getX();
        String goal;
        if (command=="front"){
            if(p.getName().equals(pName)){
                y-=2;
            }
            else{
                y+=2;
            }

        }
        else if (command == "back"){
            if(p.getName().equals(pName)) {
                y += 2;
            }
            else{
                y-=2;
            }
        }
        else if (command == "left"){
            x-=2;
        }
        else if (command == "right"){
            x+=2;
        }
        goal =y+","+x;
        return goal;
    }

    /**
     * This method places a valid fence on a random position within the board
     * @return true if the fence has been placed
     */
    private boolean addRandomFence(){
        Random random = new Random();
        int maxSize = 17;
        boolean repeat;
        int iterations=0;
        do{
            //System.out.println("CUNT");
            iterations++;
            if(iterations>100) {
                //System.out.println("LAZY CUNT MOMENT");
                board.getP().moveTo(new int[]{board.getP().getGoalRow(), 0});
                return true;
            }
            repeat = false;
            //random direction chosen horizontal or vertical
            int randDirection = random.nextBoolean()?1:0;
            // from 0-16 random numbers
            int x =random.nextInt(maxSize);
            int y = random.nextInt(maxSize);
            if(randDirection==0){
                if(x%2!=0){
                    x += 1;
                }
                if(y%2==0){
                    y += 1;
                }
            }
            else{
                if(x%2==0){
                    x += 1;
                }
                if(y%2!=0){
                    y += 1;
                }
            }

            String[] data = {String.valueOf(randDirection),y+","+x};
            Fence fence = new Fence(data);
            if(fence.isValid(board)){
                if(p.placeFence()){
                    board.placeFence(fence);
                    return true;
                }
                else{
                    repeat = true;
                }
            }
            else{
                repeat = true;
            }
        }while(repeat);
        return false;
    }


    /**
     * This method generates the output of choosing the placement of fence or moving
     * @param percentage the amount of probabaility
     * @return the probability result
     */
    static boolean movementOrFenceChoice(int percentage){
        Random random = new Random();
        int randNum = random.nextInt(100)+1;
        return randNum <= percentage;
    }



}