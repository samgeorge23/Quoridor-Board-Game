package upei.project;

import java.util.Random;

/**
 * Strategy algorithim for the game Quoridor
 * <p>
 * @author Dale Urquhart et al.
 */
class BFSStrategy extends BFS{
    /**
 * Constructor takes current board state, decides on a move, and applies it to the board state.
 * <p>
 * @param board running instance of the game board
 * @return null
 */
    protected BFSStrategy(Board board, int j) {
        super(board, null);
        double probability = 0.7;
        double randomValue = Math.random(); 
        String sGoal="";
        boolean placingFence = false;
        String[] data = new String[2];
        if(randomValue<probability && board.getCurrentPlayer().getFencesLeft()>0) {
            Fence f = getNextFence(board);
            if(f==null) {
                board.getCurrentPlayer().moveTo(new int[]{board.getCurrentPlayer().getGoalRow(), 0});
                return;
            }
            placingFence = true;
            if(f.isVert()) {
                data[0]="1";
            } else{data[0] = "0";}
            
            int[] uL = f.getCoords()[0];
            data[1] = "("+Integer.toString(uL[0])+","+Integer.toString(uL[1])+")";

        } else{
            int[] move = getNextMove(board);
            if(move == null) {
                board.getCurrentPlayer().moveTo(new int[]{board.getCurrentPlayer().getGoalRow(), 0});
                return;
            } 
            sGoal = Integer.toString(move[0])+","+Integer.toString(move[1]);
        } if(j>200){
            board.getCurrentPlayer().moveTo(new int[]{board.getCurrentPlayer().getGoalRow(), 0});
        }
        board.updateBoard(placingFence, sGoal, data);
    }

    /**
 * Calls and returns the value of findPath() passing in the running instance of the board
 * <p>
 * @param b running instance of the game board
 * @return the move that will lead to the shortest path to win
 */
    private int[] getNextMove(Board b) {
        return findPath(new Board(b));
    }
    
    
     /**
 * Randomly iterates through possible fence configurations returns the first valid fence placement it finds on the board
 * <p>
 * @param b running instance of the game board
 * @return the fence currentPlayer will place on current turn
 */
    private Fence getNextFence(Board b) {
        Board board = new Board(b);
        Random random = new Random();
        int maxSize = Board.getSize();
        boolean repeat;
        int iters=0;
        do{
            iters+=1;
            if(iters>100) {
                return null;
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
                if(board.getCurrentPlayer().getFencesLeft()>0){
                    return fence;
                }
                else{
                    repeat = true;
                }
            }
            else{
                repeat = true;
            }
        }while(repeat);
    //no possible fences
    return null;
    }
}
