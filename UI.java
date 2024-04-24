import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;
/**
 * This class provides a text based user interface for the player to interact with the game
 * @author Lauren Scott
 * @version student sample code (Edited by Theodora)
 */
public class UI {
    private Sudoku thegame;//this is the game model
    private String menuChoice;//this is the users choice from the menu
    private Scanner reader;//this scanner is used to read the terminal
    private Stack<Assign> stack;
    private Stack<Assign.Placement> history;
    private String level;
    
    /**
     * Constructor for the class UI
     */
    public UI(String difficulty) {
        thegame = new Sudoku(difficulty);
        stack = new Stack<>();
        reader = new Scanner(System.in);
        menuChoice="";
        while(!menuChoice.equalsIgnoreCase("Q")&&!thegame.checkWin()) {
            displayGame();
            menu();
            menuChoice = getChoice();

        }
        if (thegame.checkWin()) {
            winningAnnouncement();
        }
    }

    /**
     * Method that outputs an announcement when the user has won the game
     */
    public void winningAnnouncement() {
        System.out.println("Congratulations, you solved the puzzle");
    }

    /**
     * Method that displays the game for the user to play
     */
    public void displayGame() {
        //boardmoves = thegame.getMoves();
        if (thegame.getGameSize() == 9) {
            System.out.println("Col   0 1 2 3 4 5 6 7 8");
            System.out.println("      - - - - - - - - -");
        } else {
            System.out.println("Col   0 1 2 3 ");
            System.out.println("      - - - - ");
        }

        for (int i = 0; i < thegame.getGameSize(); i++) {
            System.out.print("Row "+i+"|");
            for (int c = 0; c < thegame.getGameSize(); c++) {
                if (thegame.getGameSize() == 9) {
                    if (c == 2 || c == 5 || c == 8) {
                        if (thegame.getIndividualMove(i,c).contains("-") ){
                            System.out.print(" " + "|");
                        } else{
                            System.out.print(thegame.getIndividualMove(i,c) + "|");
                        }
                    } else {
                        if (thegame.getIndividualMove(i,c).contains("-") ){
                            System.out.print(" " + ".");
                        } else{
                            System.out.print(thegame.getIndividualMove(i,c) + ".");
                        }
                    }

                } else if (thegame.getGameSize() == 4) {
                    if (c == 1 || c == 3) {
                        if (thegame.getIndividualMove(i,c).contains("-") ){
                            System.out.print(" " + "|");
                        } else{
                            System.out.print(thegame.getIndividualMove(i,c) + "|");
                        }
                    } else {
                        if (thegame.getIndividualMove(i,c).contains("-") ){
                            System.out.print(" " + ".");
                        } else{
                            System.out.print(thegame.getIndividualMove(i,c) + ".");
                        }
                    }

                
                }
            }if (thegame.getGameSize() == 9 && (i == 2 || i == 5|| i == 8)) {
                System.out.println("\n      - - - - - - - - -");

            } else if (thegame.getGameSize() == 9 ){
                System.out.println("\n      .................");

            } else if (thegame.getGameSize() == 4 && (i==1||i==3) ){
                System.out.println("\n      - - - - ");

            } else {
                System.out.println("\n     .........");
            }
        }
    }

    /**
     * Method that displays the menu to the user
     */
    public void menu() {

        System.out.println("Please select an option: \n"
            + "[M] make move\n"
            + "[S] save game\n"
            + "[L] load saved game\n"
            + "[U] undo move\n"
            + "[C] clear game\n"
            + "[Q] quit game\n");

    }

    /**
     * Method that gets the user's choice from the menu and conducts the activities
     * accordingly
     * @return the choice the user has selected
     * 
     */
    public String getChoice() {
        String choice = reader.next();
        if (choice.equalsIgnoreCase("M")) {
            System.out.print("Which row is the cell you wish to fill?  ");
            String row = reader.next();
            System.out.print("Which colum is the cell you wish to fill?  ");
            String col = reader.next();
            System.out.print("Which number do you want to enter?  ");
            String number = reader.next();
            
            if(!thegame.makeMove(row, col, number)) {
                System.out.println("That cell cannot be changed");
                while (!thegame.makeMove(row, col, number)) {
                    System.out.print("Which row is the cell you wish to fill?  ");
                    row = reader.next();
                    System.out.print("Which colum is the cell you wish to fill?  ");
                    col = reader.next();
                    System.out.print("Which number do you want to enter?  ");
                    number = reader.next();
                    
                }thegame.makeMove(row, col, number);
            }

        } else if (choice.equalsIgnoreCase("S")) {
            saveGame("UsersSavedMoves.txt");
        } else if (choice.equalsIgnoreCase("U")) {
            undoMove();
        } else if (choice.equalsIgnoreCase("L")) {
            loadGame();
        } else if (choice.equalsIgnoreCase("C")) {
           // clearGame( difficulty);
        } else if (choice.equalsIgnoreCase("Q")) {
            System.exit(0);
        }
        return choice;
    }

    /**
     * saveGame 
     * To be implemented by student - this method should undo the previous move made in the game, along with the corresponding computer move
     */
    public void saveGame(String fileName) {
        //System.out.println("Code not yet implemented");
        try (FileWriter writer = new FileWriter("UsersSavedMoves.txt")) {
            for (int i = 0; i < thegame.getGameSize(); i++) {
                for (int c = 0; c < thegame.getGameSize(); c++) {
                    writer.write(i + " " + c + " " + thegame.getIndividualMove(i, c) + "\n");
                }
            }
            System.out.println("Game saved!");
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    /**
     * undoMove 
     * To be implemented by student - this method should undo the previous move made in the game, along with the corresponding computer move
     */
    public void undoMove() {
        // Reset the last filled cell to its initial state
           if (!history.isEmpty()) {
             Assign.Placement placement = history.pop();
             //thegame.makeMove(placement.entryRow, placement.entryCol, 0);
             System.out.println("Undo successful.");
        } else {
            System.out.println("No moves to undo.");
        } 
        //return clearCell();
    }
    

    /**
     * loadGame
     * To be implemented by student - this method should load a previous saved game
     */
    public  void loadGame() {
        //System.out.println("Code not yet implemented");
        try (Scanner fileReader = new Scanner(new File("UsersSavedMoves.txt"))) {
            while (fileReader.hasNextLine()) {
                String row = fileReader.nextLine();
                String[] rowElements = row.split(" ");
                
                thegame.makeMove(rowElements[0], rowElements[1], rowElements[2]);
                
            }
            System.out.println("Last saved game from " + "UsersSavedMoves.txt");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
         
    
    }



    /**
     * The main method within the Java application. 
     * It's the core method of the program and calls all others.
     */
    public static void main(String args[],String difficulty) {
        UI thisUI = new UI(difficulty);
    }
}//end of class UI