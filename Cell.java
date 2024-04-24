import javax.swing.JButton;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;

/**
 * Write a description of class Cell here.
 *
 * @author Theodora
 * @version 14th Jan 2024
 */
public class Cell extends JButton implements Observer {
    private int row;
    private int col;
    private Sudoku sudoku;
    private Cell[][] cellButtons;
    

    public Cell(int row, int col, Sudoku sudoku) {
        this.row = row;
        this.col = col;
        this.sudoku = sudoku;
        setOpaque(true);
        setBorderPainted(true);
        
         sudoku.getMoves()[row][col].addObserver(this);
        
        // Update the button text based on the initial state
        updateButton();

        addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCellButtonClick(row, col);
            }
        });
    }

    private void handleCellButtonClick(int row, int col) {
        if (sudoku.getMoves()[row][col].getFillable()) {
            String userInput = getUserInput("Enter a number (1-9):");

            if (userInput != null && !userInput.isEmpty()) {
                try {
                    int number = Integer.parseInt(userInput);
                    if (!isValidNumber(number)) {
                        JOptionPane.showMessageDialog(null, "Invalid number. Please enter a number between 1 and 9.");
                    }
                    else {
                        if (!sudoku.makeAMove(row, col, String.valueOf(number))) {
                            
                            System.out.println ("invalid User move");

                        }
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "This cell can't be altered.");
        }
    }


    private boolean isValidNumber(int number) {
    return number >= 1 && number <= 9;
}


     private String getUserInput(String message) {
        return JOptionPane.showInputDialog(message);
        
    }
    
    public void  retriveInput(){
    }


    public void updateCellButton(int row, int col, boolean getState) {
            setText(sudoku.getMoves()[row][col].getState());
    }

    private boolean isValidInput(String userInput) {
        //check if userInput is a valid number between 1 and 9
        return userInput.matches("[1-9]");
    }

    public void updateButton() {
        // Update the button text based on the Sudoku game state
        setText(sudoku.getMoves()[row][col].getState());
        
        // Set background color based on 3x3 grid
        int gridRow = row / 3;
        int gridCol = col / 3;

        if ((gridRow + gridCol) % 2 == 0) {
            // Light grey background
            setBackground(Color.LIGHT_GRAY);
        } else {
            
            setBackground(Color.GRAY);
        }
    }
    
    
    @Override
    public void update(Observable o, Object arg) {
        // Update the button text when the corresponding Slot changes
        updateButton();
    }


}

