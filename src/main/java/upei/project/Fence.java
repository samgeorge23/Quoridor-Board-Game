package upei.project;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Fence is used to block players from reaching their goal and can be placed in fence reserved spaces
 */
class Fence {
    /**
     * Determines whether the fence is vertical or horizontal
     */
    private boolean isVert;
    /**
     * Spans the coordinates of the fence
     */
    private int[][] coords;
    /**
     * Determines whether the created fence is according to the board game rules
     */
    private boolean validFence = true;

    /**
     * The Fence constructor is used to take the upper left coordinates or the coordinates of the potential fence and create it
     * @param sData contains the orientation and the prospective coordinates to create the fence
     */
    protected Fence(String[] sData) {
        int vert = validateVert(sData);
        if(vert != -1) {
            this.isVert = (vert==0) ? false : true;
        } else this.validFence = false;
        
        this.coords = organizeCoords(sData);
        if(this.coords == null) {
            this.validFence = false;
        }
    }

    //organize isVert return 1 if true, 0 if false, -1 if bad input

    /**
     * Checks and updates the orientation of the fence based on if the fence is vertical in process of creation
     * @param sData sData contains the orientation and the prospective coordinates to create the fence
     * @return return 1 if true, 0 if false, -1 if bad input
     */
    private static int validateVert(String[] sData) {
        int data;
        try{
            data = Integer.parseInt(sData[0]);
        } catch(Exception E) {
            return -1;
        }
        if(data == 0 || data == 1){
            return data;
        }
        return -1;
    }

    //return null if out of bounds or bad input else return int[][] coords

    /**
     * Processes the string data to the proper coordinates of the fence
     * @param sData sData contains the orientation and the prospective coordinates to create the fence
     * @return the processed coordinates of the whole fence
     */
    private int[][] organizeCoords(String[] sData) {
        //organize coords
        int[][] coords = new int[2][2];
        String trimmed;
        if(sData == null || sData[0] == null || sData[1] == null){
            return null;
        }
        trimmed = sData[1].replaceAll("[()]", "");
        String[] pairs = trimmed.split(",");

        int index = 0;
        for (int i = 0; i < pairs.length; i += 2) {
            int[] coord = coords[index];
            //are coords int?
            try {
                coord[0] = Integer.parseInt(pairs[i]);
                coord[1] = Integer.parseInt(pairs[i + 1]);
            } catch(Exception E) {
                return null;
            }

            //check if coords are in bounds
            if(coord[0] > 16 || coord[0] < 0 || coord[1] > 16 || coord[1] < 0) {
                return null;
            }

            index++;
        }
        if(pairs.length==2){
            return generateBottomRightCoord(coords);
        }
        return coords;
    }

    /**
     * Generates the bottom right coordinate of the fence when only the upper left coordinate of the fence is provided
     * @param coords the half empty coordinates of the whole fence
     * @return the coordinates of the whole fence
     */
    private int[][] generateBottomRightCoord(int[][] coords){
        if(isVert){
            coords[1][0] = coords[0][0]+2;
            coords[1][1] = coords[0][1];
        }
        else{
            coords[1][0] = coords[0][0];
            coords[1][1] = coords[0][1]+2;
        }
        if(coords[1][0] > 16 || coords[1][0] < 0 || coords[1][1] > 16 || coords[1][1] < 0) {
            return null;
        }
        return coords;
    }

    /**
     * Checks if the created fence is out of bounds, is on the fence reserved space on the board, is the correct size
     * of the fence, is overlapping other fences or if the fences completely blocked the player's pathway
     * @param board the board for the fences and players to interact
     * @return true if the potential fence passes all the conditions and rules of the game
     */
    protected boolean isValid(Board board) {
        ArrayList<Fence> fences = board.getFences();

        //bad input/out of bounds?
        if(!validFence) {
            return false;
        }
        if(!isFenceOnAllocatedFenceSpace()){
            return false;
        }

        //is size legal?
        if(illegalSize(this.getCoords())) {
            return false;
        }

        //is fence overlapping? 
        if(fenceOverlaps(fences)){
            return false;
        }
        
        //does fence block win?
        BFS bfs = new BFS(board, this);
        return bfs.solution;
    }


    /**
     * This method ensures that the fence is only placed on the fence reserved spaces on the board.
     * @return true if fence is placed on a fence reserved space
     */
    private boolean isFenceOnAllocatedFenceSpace() {
        int uL[] = coords[0];

        if(isVert){
            if(uL[0]%2==0 && uL[1]%2!=0){
                return true;
            }
        }
        else{
            if(uL[0]%2!=0 && uL[1]%2==0){
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if the fence is the correct size of spanning two player reserved cells
     * @param coords the coordinates of the whole fence
     * @return true if the fence is the right size
     */
    private boolean illegalSize(int[][] coords) {
        int[] uL = coords[0];
        int[] bR = coords[1];

        if(isVert==true){
            if(uL[0] != bR[0]-2 || uL[1] != bR[1]) {
                return true;
            }
        }
        else{
            if(uL[0] != bR[0] || uL[1] != bR[1]-2) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if the potential fence is overlapping the existing fences on the board
     * @param fences the list of the existing fences placed on the board
     * @return true if the potential fence does not overlap other fences
     */
    private boolean fenceOverlaps(ArrayList<Fence> fences) {
        int[] TL = this.getCoords()[0];
        int[] BR = this.getCoords()[1];
        int[] M = {(int)(TL[0] + BR[0])/2,(int)(TL[1] + BR[1])/2};
        int[][] currentFenceCoords = {TL,BR,M};

        for(Fence f : fences) {
            int[] fTL = f.getCoords()[0];
            int[] fBR = f.getCoords()[1];
            int[] fM = {(int)(fTL[0] + fBR[0])/2,(int)(fTL[1] + fBR[1])/2};
            int[][] otherFenceCoords = {fTL,fBR,fM};

            for(int[] currentCoord:currentFenceCoords){
                for(int[] otherCoord:otherFenceCoords){
                    if(Arrays.equals(currentCoord,otherCoord)){
                        return true;
                    }
                }
            }

        }
        return false;
    }
    
    
    /** 
     * @return boolean
     */
    //get isvert
    protected boolean isVert(){
        return this.isVert;
    }

    //get in[][]coords
    protected int[][] getCoords() {
        return Arrays.copyOf(this.coords, this.coords.length);
    }

    protected boolean isValidFence(){
        return this.validFence;
    }
}
