import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Write a description of class ButtonCtrls here.
 *
 * @author Theodora Orji
 * @version 14th Jan 2024
 */
public class ButtonCtrls implements ActionListener {

    private Slot slot;
    private JFrame parentFrame;
    private Scanner reader;
    private Cell[][] cellButtons;
    private GUI sudokugui;

    private Sudoku thegame;


    /**
     * Constructor for objects of class ButtonCtrls
     */
    public ButtonCtrls(Slot slot, JFrame parentFrame, Cell[][] cellButtons, Sudoku thegame) {
        this.thegame = thegame;
        reader = new Scanner(System.in);
        this.slot = slot;
        this.parentFrame = parentFrame;
        this.cellButtons = cellButtons;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("Save".equals(command)) {
            saveGame("UsersSavedMoves.txt", cellButtons);
            JOptionPane.showMessageDialog(parentFrame, "Game saved.");
        } else if ("Load".equals(command)) {
            loadGame(cellButtons);
            JOptionPane.showMessageDialog(parentFrame, "Last game loaded.");

        } else if ("Clear".equals(command)) {
            clearGame(cellButtons);
            JOptionPane.showMessageDialog(parentFrame, "Board cleared");
        } else if ("Undo".equals(command)) {
            undoMove( cellButtons);
        }
    }

    public void loadGame(Cell[][] cellButtons) {
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


    public void saveGame(String fileName, Cell[][] cellButtons) {

        try (FileWriter writer = new FileWriter("UsersSavedMoves.txt")) {
            for (int i = 0; i < thegame.getGameSize(); i++) {
                for (int c = 0; c < thegame.getGameSize(); c++) {
                    writer.write(i + " " + c + " " + thegame.getIndividualMove(i, c) + "\n");
                }
            }
            System.out.println("Game saved to" + "UsersSavedMoves.txt");
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    public void undoMove(Cell[][] cellButtons ) {
        thegame.undoMove(cellButtons);
    }

    public void clearGame(Cell[][] cellButtons) {
        for (int row = 0; row < thegame.getGameSize(); row++) {
            for (int col = 0; col < thegame.getGameSize(); col++) {
                if (thegame.makeAMove(row, col, "-")) {
                    cellButtons[row][col].updateButton();
                }
            }
        }
    }
    }





