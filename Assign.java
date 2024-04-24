
/**
 * Assign
 * This class handles the creation of all moves in the game
 * @author Lauren Scott
 * @version Student Sample Code (Edited By Theodora)
 */
public class Assign {
    private int col, row;//The row and column being assigned
    private Sudoku game;//The game
    private Slot slot;
    Slot[][] moves;//2D Array to store the game's moves
    /**
     * Constructor for Assign class.
     * This gets the total number of moves and calls methods to determine the row that will be filled, and to set the state of the slot being assigned.
     * @param game - the game
     * @param col - the column the user has selected
     * @param player - a Boolean value that determines whether it is a player/computer move
     */
    public Assign(Sudoku game, int row,int col, String number,Slot slot){
        this.game = game;
        this.col = col;
        this.row = row;
        assignMove(number);
        this.slot = slot;
        
        
    }
    
    /**
     * assignMove
     * This method assigns the move to the game
     * @param player a Boolean value to determine whether it is a computer/player move
     */
    public void assignMove( String number) {
        moves[row][col].setState(number);

    }
    /**
     * getRow
     * This method returns the current row value for this move. It allows another element of the program to access this move's current row.
     * @return the row value
     */
    public int getRow() {
        return row;
    }
    
    public int getCol() {
        return col;
    }
    public String getState() {
        return slot.getState();
    }
    // Created a subclass within the Assign Class to handle move placement 
    static class Placement {
        int entryRow, entryCol;
        String entry;
        
        Placement(int row, int col, String entry) {
            this.entryRow = row;
            this.entryCol = col;
            this.entry = String.valueOf(entry);
        }
    }
}//End of class Assign
