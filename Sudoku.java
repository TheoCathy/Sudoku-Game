import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Observable;
import javax.swing.JOptionPane;

/**
 * This is the class Sudoku and it handles the functionality of the main game.
 * @author Lauren Scott
 * @version Student Sample Code edited by Theodora
 */
public class Sudoku extends Observable{
    private String[][] solution;//This array stores the solution to the game
    private Slot[][] populatedBoard;//This is the board of moves for the game
    private Scanner reader;//This scanner is used to read the game and level files
    private int gameSize;    //This will be the size of the game
    private String level;   //This is the level file,changeable for easy and hard

    private Stack<Assign.Placement> history = new Stack<>();
    private String Solution;
    
    /**
     * This is the constructor for the class Sudoku
     */
    public Sudoku(String difficulty) {

        updateLevel(difficulty);
    }

    public void updateLevel(String difficulty) {
        if ("easy".equals(difficulty)) {
            this.level = "Levels/esu1.txt";
            
        } else if ("hard".equals(difficulty)) {
            this.level = "Levels/su1.txt";
            
        }
        try {
            reader = new Scanner(new File(level));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        gameSize = calculateGameSize();
        solution = new String[gameSize][gameSize];
        populatedBoard = new Slot[gameSize][gameSize];
        readLevelFile();
        loadWinSolution(difficulty);
        history.clear();
        setChanged();
        notifyObservers();
    }
    /**
     * This method gets the entire set of moves in the game
     * @return the set of moves
     */
    public Slot[][] getMoves() {
        return populatedBoard;
    }
    /**
     * This method gets an individual cell's state
     * @param row - the row of the move
     * @param col - the column of the move
     * @return The state of that cell
     */
    public String getIndividualMove(int row, int col) {
        return populatedBoard[row][col].getState();
    }
    /**
     * This method reads the game size from the file 
     * @return the size of the puzzle
     */
    public int calculateGameSize() {
        return Integer.parseInt(reader.next());
    }
    /**
     * This method provides access to the gameSize from other classes
     * @return the size of the puzzle
     */
    public int getGameSize() {
        return gameSize;
    }
    /**
     * This method reads the level file to populate the game
     * @return The moves stored in the file
     */
    public Slot[][] readLevelFile() {
    
        while (reader.hasNext()) {
            int row =Integer.parseInt(reader.next());
            int col =Integer.parseInt(reader.next());
            String move = reader.next();
            
            populatedBoard[row][col] = new Slot(col, row, move, false);
            
        }
        return populatedBoard;
    }
    /**
     * This method reads the solution file that corresponds to the level file
     */
    public void loadWinSolution(String difficulty) {

        String solutionFilePath;

        if ("easy".equals(difficulty)) {
            solutionFilePath = "Solutions/esu1solutions.txt";
        } else if ("hard".equals(difficulty)) {
            solutionFilePath = "Solutions/su1solutions.txt";
        } else {
            // Handle invalid difficulty level
            System.err.println("Invalid difficulty level.");
            return;
        }

        try {
            reader = new Scanner(new File("Solutions/esu1solution.txt"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while (reader.hasNext()) {
            int row =Integer.parseInt(reader.next());
            int col =Integer.parseInt(reader.next());
            String move = reader.next();
            solution[row][col] = move;
            
        }
        
    }
    /**
     * This method checks whether the game has been won
     * @return whether the game has been won
     */
    public Boolean checkWin(){
        for (int i = 0; i<gameSize; i++) {
            for (int c = 0; c <gameSize; c++) {
                if (!populatedBoard[i][c].getState().equals(solution[i][c])) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * This method allows a user to make a move in the game
     * @param row - the row of the move
     * @param col - the column of the move
     * @param number - the number they are wishing to enter in the cell
     * @return whether the move was valid
     */
    public Boolean makeMove(String row, String col, String number){
        int enteredRow = Integer.parseInt(row);
        int enteredCol = Integer.parseInt(col);
        if (populatedBoard[enteredRow][enteredCol].getFillable()){
            history.push(new Assign.Placement(Integer.parseInt(row), Integer.parseInt(col), (number)));
            populatedBoard[enteredRow][enteredCol].setState(number);

            return true;
        } else {
            return false;
        }
    }

    public boolean makeAMove(int row, int col, String number) {
        if (populatedBoard[row][col].getFillable()) {
            // Save the current state before making the move
            history.push(new Assign.Placement(row, col, number));
            populatedBoard[row][col].setState(number);
            //System.out.println("History stack size: " + history.size());
          // To check each move a player moves to see if they win the game
            if (checkWin()) {
                GameWon();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    private boolean GameWon() {
        JOptionPane.showMessageDialog(null, "CONGRATULATIONS! You Won the Game! Sudoku Master");


        return true;
    }
    public void undoMove(Cell[][] cellButtons ) {
        // Reset the last filled cell to its initial state
        if (!history.isEmpty()) {
            Assign.Placement placement = history.pop();
            makeAMove(placement.entryRow, placement.entryCol, "-");
            cellButtons[placement.entryRow][placement.entryCol].updateButton();
            JOptionPane.showMessageDialog(null, "Undo  Successful");

        } else {
            JOptionPane.showMessageDialog(null, "No moves to undo.");
            System.out.println("No moves to undo.");
        }
    }

    public Slot[][] getNewBoard(){
        return new Slot[gameSize][gameSize];

    }

}//end of class Sudoku
