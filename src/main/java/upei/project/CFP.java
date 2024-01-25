package upei.project;

import java.util.Random;

/**
 * Close fence strategy is a strategy method to place the fence close in front of the opponents direction to make
 * it difficult for the opponent to reach its goal
 */
public class CFP {
    /**
     * The player implementing the strategy
     */
    Player p;
    /**
     * The opponent recieving the strategy
     */
    Player opp;
    /**
     * The state of the board and its contents
     */
    Board board;
    /**
     * The probability of the player making the movement than placing fence
     */
    int percentageChance = 65;
    int j;
    /**
     * This constructor makes the player implement the strategy of placing the closest fence or moving on the board
     * @param board the cureent state of the board
     */
    CFP(Board board, int j){
        this.j=j;
        if(j>200) {
            board.getCurrentPlayer().moveTo(new int[]{board.getCurrentPlayer().getGoalRow(), 0});
       }
       this.p = board.getCurrentPlayer();
       this.opp = board.getCurrentOpp();
       this.board = board;

       solve();

//       board.switchPlayers();
    }

    /**
     * This method decides and performs the action whether the player should place fence or move
     */
    void solve(){
        if(p.getFencesLeft()==0){
            percentageChance =100;
        }
        if(CFP.movementOrFenceChoice(percentageChance)){
            move();
            //System.out.println("Current Player Chance: "+p.getName()+" moved");
        }
        else{
            if(!addCloseFence()){
                move();
                //System.out.println("Current Player Chance: "+p.getName()+" moved");
            }
            else{
                //System.out.println("Current Player Chance: "+p.getName()+" placed fence");
            }

        }
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
            if(iters>100 || this.j >200) {
                board.getCurrentPlayer().moveTo(new int[]{board.getCurrentPlayer().getGoalRow(), 0});
                return;
            }
            int randNum = random.nextInt(3);
            if(board.updateBoard(false,futureMove(commandArray[randNum]),null).succesfullUpdate){
                moved = true;
            }
        }


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
     * This method places the closest fence that could stop opponent from advancing that direction
     * @return true if the fence has been placed
     */
    boolean addCloseFence(){
        int[][] horizontalCoords = generateHorizontalFenceCoords();
        int[][] verticalCoords = generateVerticalFenceCoords();
        String[] fenceInput;
        for(int i=0;i<4;i++){
            if(i>=2){
                fenceInput = new String[]{"1",verticalCoords[i-2][0]+","+verticalCoords[i-2][1]};
            }
            else{
                fenceInput = new String[]{"0",horizontalCoords[i][0]+","+horizontalCoords[i][1]};
            }
            if(board.updateBoard(true,"",fenceInput).succesfullUpdate){
                return true;
            }
        }

        for(int i=0;i<4;i++){
            if(i>=2){
                fenceInput = new String[]{"1",verticalCoords[i][0]+","+verticalCoords[i][1]};
            }
            else{
                fenceInput = new String[]{"0",horizontalCoords[i+2][0]+","+horizontalCoords[i+2][1]};
            }
            if(board.updateBoard(true,"",fenceInput).succesfullUpdate){
                return true;
            }
        }

        return false;

    }

    /**
     * This method generates all the horizontal fences closes to the opponent
     * @return the multiple horizontal coordinates for fence placements
     */
    int[][] generateHorizontalFenceCoords(){
        int [][] horizontalCoords = new int[4][0];
        int y = opp.getY();
        int x = opp.getX();
        int temp_y = y + 1;
        int temp_x = x -4;
        for(int i=0;i<2;i++){
            temp_x += 2;
            horizontalCoords[i] = new int[]{temp_y,temp_x};
        }
        temp_y = y - 1;
        temp_x = x -4;
        for(int i=2;i<4;i++){
            temp_x += 2;
            horizontalCoords[i] = new int[]{temp_y,temp_x};
        }
        return horizontalCoords;
    }

    /**
     * This method generates all the vertical fences closes to the opponent
     * @return the multiple vertical coordinates for fence placements
     */
    int[][] generateVerticalFenceCoords(){
        int [][] verticalCoords = new int[4][0];
        int y = opp.getY();
        int x = opp.getX();
        int temp_y = y -4;
        int temp_x = x -1;
        for(int i=0;i<2;i++){
            temp_y += 2;
            verticalCoords[i] = new int[]{temp_y,temp_x};
        }
        temp_y = y - 4;
        temp_x = x +1;
        for(int i=2;i<4;i++){
            temp_y += 2;
            verticalCoords[i] = new int[]{temp_y,temp_x};
        }
        return verticalCoords;
    }


}