import java.util.Observable;

/**
 * Slot
 * This class handles the state of each slot in the game. Extending observable in preparation for the addition of a GUI.
 * @author Lauren Scott
 * @version Student Sample Code(edited by Theodora)
 */
public class Slot extends Observable{
    private String state;//The current state of the slot
    private int row, col;//The row and column number of the slot 
    private Boolean fillable;//whether that slot can be changed
   
    /**
     * Constructor of the class slot
     * This creates the slot and denotes where it is placed in the game board.
     * 
     * @param col - the slot's column number
     * @param row - the slot's row number
     * @param number - the number that is in that cell
     */
    public Slot (int col, int row, String number) {
        this.row = row;
        this.col = col;
        this.state = number;
        this.fillable = true;
        
    }
    /**
     * Constructor of the class slot when importing the level file
     * This creates the slot and denotes where it is placed in the game board.
     * 
     * @param col - the slot's column number
     * @param row - the slot's row number
     * @param number - the number that is in that cell
     * @param fillable - whether that cell can be filled  by the user, cells in the level file that already have numbers should not be changed.
     */
    public Slot (int col, int row, String number, Boolean fillable) {
        this.row = row;
        this.col = col;
        this.state = number;
        if (!number.contains("-") ){
            this.fillable = false;
        } else {
            this.fillable = true;
        }
    }
    /**
     * setState
     * This method sets the current state of the slot
     * @param newState - the new state of the slot
     */
    public void setState(String newState) {
        if(isValidState(newState) == true) {
            this.state = newState;
            setChanged();
            notifyObservers(newState); 
        } 
    }
    /**
     * getState
     * This provides the current state of the slot
     * @return the current state of the slot
     */
    public String getState(){
        return state;
        
    }
        
        /**
         * isValidState 
         * This method checks whether the selected state is valid
         * @param state - the state that is being checked
         * @return a Boolean value showing whether the state is valid or not
         */
        public static boolean isValidState(String state) {
            if (!state.contains("-") ){
            try {
                Integer.parseInt(state);
            } catch (Exception e) {
                return false;            
            }
            
        }   
        return true;
    }
        
        public static boolean isValidStateGUI(String state) {
        if (!state.contains("-")) {
            try {
                int number = Integer.parseInt(state);
                return number >= 1 && number <= 9; 
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false; 
       }
    

     /**
         * This method checks whether the cell the user is wanting to interact with can be filled
         * @return whether it can be filled.
         */
        public Boolean getFillable() {
            return fillable;
        }
        
}//End of class Slot

