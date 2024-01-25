package upei.project;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Runs a simulation and conducts 20 trials and calculates the win count of all the combinations among the strategies
 * such as RFP, BFS and CFP played against each other
 */
public class SimulationExperiment {

    public static void main(String[] args) {
        SimulationExperiment runner = new SimulationExperiment();
        runner.run();
    }
    Player p;
    Player opp;
    Board board;
    Player[] players;
    String[] fenceInput;
    boolean firstInput;
    boolean isFence;
    boolean fenceExit;
    boolean end;
    boolean quit;
    String command;
    Scanner input;

    /**
     * This method runs simulation of all the strategies
     */
    void run() {
        // creates the board to play
        this.p = new Player(new int[]{16,8}, "P1", 0);
        this.opp = new Player(new int[]{0,8}, "P2", 16);
        this.board = new Board(this.p, this.opp);
        this.players = new Player[] {this.p, this.opp};
        this.fenceInput = new String[2];
        this.firstInput = true;
        this.isFence = false;
        this.fenceExit = false;
        this.end = false;
        this.command = "";
        this.input = new Scanner(System.in);

        System.out.println("Enter '1' to play the 2-player Quoridor board game or enter '0' analyze the outcome of the computer based strategies on the board game?\n");
        int option = this.input.nextInt();
        if(option==0){
            BFSvsCFP(board);
            BFSvsRFP(board);
            CFPvsRFP(board);
        }
        else if(option==1){
            System.out.println("Welcome to the board game QUORIDOR!!!");
            System.out.println("To move the player as you want:\n\tinput coordinates in the format a,b for row and col respectively or:\n\t'front' to go forward\n\t'back' to go backward\n\t" +
                    "'left' to go left\n\t'right' to go right\nTo place a fence as you want:\n\t'fence' to indicate fence placing\n\t" +
                    "vert for a vertical fence or horz for a horzontal fence\n\t(a,b),(c,d) for a fence to cover the affected tiles (a,b) for upper left and (c,d) for lower right");
            System.out.println("Enter the command 'exit' if you want to leave the board game.");
            pvp();
        }
        else{
            System.out.println("Wrong command! rerun the program.");
        }
    }

    /**
     * This method makes the BFS strategy play against CFP strategy
     * @param board the current state of the board
     */
    private static void BFSvsCFP(Board board) {
        board = new Board(board);
        System.out.println("BFS vs CFP");
        //per full turn cycle
        board.getP().setName("BFS");
        board.getOpp().setName("CFP");
        Board simBoard;
        boolean isBFS = true;
        
        for(int i=0; i<20; i++) {
            isBFS = true;
            simBoard = new Board(board);

            while(!simBoard.isOver()) {
                if(isBFS) {
                    new BFSStrategy(simBoard, 0); 
                    isBFS = false;
                } else {
                    new CFP(simBoard,0);
                    isBFS = true;
                }

                if(simBoard.isOver()){
                    break;
                }
            }
            
            if(simBoard.getCurrentOpp().equals(board.getP())) {
                board.getP().addWin();
            } else{
                board.getOpp().addWin();
            }
        }
        System.out.println(board.getP().getName()+" Won "+Integer.toString(board.getP().getWins())+" times");
        System.out.println(board.getOpp().getName()+" Won "+Integer.toString(board.getOpp().getWins())+" times");
    }


    /**
     * This method makes the BFS strategy play against RFP strategy
     * @param board the current state of the board
     */
    private static void BFSvsRFP(Board board) {
        board = new Board(board);
        System.out.println("BFS vs RFP");
        //per full turn cycle
        board.getP().setName("BFS");
        board.getOpp().setName("RFP");
        Board simBoard;
        boolean isBFS = true;
        int j=0;
        for(int i=0; i<20; i++) {
            simBoard = new Board(board);
            isBFS = true; j=0;
            while(!simBoard.isOver()) {
                j+=1;
                if(isBFS) {
                    new BFSStrategy(simBoard, j); 
                    isBFS = false;

                } else {
                    new RFP(simBoard);
                    isBFS = true;
                }

                if(simBoard.isOver()){
                    break;
                }
            }
            
            if(simBoard.getCurrentOpp().equals(board.getP())) {
                board.getP().addWin();
            } else{
                board.getOpp().addWin();
            }
        }
        System.out.println(board.getP().getName()+" Won "+Integer.toString(board.getP().getWins())+" times");
        System.out.println(board.getOpp().getName()+" Won "+Integer.toString(board.getOpp().getWins())+" times");
    }


    /**
     * This method makes the CFP strategy play against RFP strategy
     * @param board the current state of the board
     */
    private static void CFPvsRFP(Board board) {
        board = new Board(board);
        System.out.println("CFP vs RFP");
        //per full turn cycle
        board.getP().setName("CFP");
        board.getOpp().setName("RFP");
        Board simBoard;
        boolean isCFP = true;
        
        for(int i=0; i<20; i++) {
            simBoard = new Board(board);
            isCFP = true;
            int j=0;
            while(!simBoard.isOver()) {
                j++;
                if(isCFP) {
                    
                    new CFP(simBoard, j);
                    isCFP = false;

                } else {
                    new RFP(simBoard);
                    isCFP = true;

                }

                if(simBoard.isOver()){
                    break;
                }
            }
            //simBoard.display_board();

            if(simBoard.getCurrentOpp().equals(board.getP())) {
                board.getP().addWin();
            } else{
                board.getOpp().addWin();
            }
        }
        System.out.println(board.getP().getName()+" Won "+Integer.toString(board.getP().getWins())+" times");
        System.out.println(board.getOpp().getName()+" Won "+Integer.toString(board.getOpp().getWins())+" times");
    }

    private static void BFSvBFS(Board board) {
        board = new Board(board);
        System.out.println("BFS vs BFS");
        //per full turn cycle
        Board simBoard;
        for(int i=0; i<1000; i++) {
            System.out.println(i);
            simBoard = new Board(board);
            while(!simBoard.isOver()) {
                new BFSStrategy(simBoard,0);  

                if(simBoard.isOver()){
                    break;
                }
            }

            if(simBoard.getCurrentOpp().equals(board.getP())) {
                board.getP().addWin();
            } else{
                board.getOpp().addWin();
            }
        }
        System.out.println(board.getP().getName()+" Won "+Integer.toString(board.getP().getWins())+" times");
        System.out.println(board.getOpp().getName()+" Won "+Integer.toString(board.getOpp().getWins())+" times");
    }
    
    //ignore

    /**
     * This method runs the Quoridor board game which two players can play and provide their own input to move the
     * players and place the fence.
     *
     * To play just replace all the strategy methods in the Run method with this method and call it
     */
    private void pvp() {
        //per full turn cycle
        while(!end) {
            //for each player's turn
            for(int i=0; i<2; i++){
                //p is current player
                p = players[i];
                if(i==0) {
                    opp = players[1];
                } else{opp = players[0];}

                do {
                    quit = playerDo();
                }
                while (!quit);

                if(command.equals("exit") || fenceExit || board.isOver()){
                    end = true;
                    break;
                }

                firstInput = true;
            }
        }
        input.close();
        System.out.println("Game Over");
    }
    private boolean playerDo() {
        boolean pQuit;
        pQuit = playerTurn();
        if(!pQuit) {
            board.updateBoard(isFence, command, fenceInput);
            return board.succesfullUpdate;
        }
        return pQuit;
    }
    private boolean playerTurn() {
        if(!firstInput) {System.out.println("Bad input, try again");} else{firstInput = false;}
        isFence = false;

        board.display_board();
        System.out.println(p.getName()+"'s turn (currently at "+Arrays.toString(p.getCoords())+", "+p.getFencesLeft()+" fences left):");
        command = input.next();

        if(command.equals("exit")){
            return true;
        }

        if(command.equals("fence")){
            for(int j=0; j<2; j++) {
                fenceInput[j] = input.next();
                if(fenceInput[j] == "exit"){
                        return true;
                    }
            }
            isFence = true;
        }
        
        return false;
    }
}