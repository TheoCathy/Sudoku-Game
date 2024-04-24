import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import javax.swing.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;
import javax.swing.JOptionPane;

/**
 * Write a description of class GUI here.
 * This class will handle the User interface display of the Sudoku Game
 * @author (Theodora Orji)
 * @version (14th Jan 2024)
 */
public class GUI extends JFrame implements Observer
{
    // instantiate Sudoku game and all the buttons available for it
    private Sudoku sudoku;
    private Slot slot;
    private JButton saveBtn;
    private JButton undoBtn;
    private JButton clearBtn;
    private JButton loadBtn;
    private JButton quitBtn;
    private JButton rulesBtn;
    private JRadioButton easyRadioBtn;
    private JRadioButton hardRadioBtn;
    private Cell[][] cellButtons;
    private String level;
    private JFrame parentFrame;
    private Stack<Assign.Placement> history = new Stack<>();

    /**
     * Constructor for objects of class GUI
     */
    public GUI(String difficulty)
    {
        displayGUI(difficulty);
        sudoku = new Sudoku(difficulty); // Initialize the Sudoku game
        sudoku.addObserver(this); // Add GUI as an observer to Sudoku
        updateBoard();
    }

    private void displayGUI(String difficulty){
        JFrame frame = new JFrame();
        sudoku = new Sudoku( difficulty);
        JPanel panel = createPanel();
        JPanel controlBtnPanel = buttonsPlane();



        frame.add(panel, BorderLayout.CENTER);
        frame.add(controlBtnPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("My Sudoku Game");
        frame.pack();
        frame.setVisible(true);



    }


    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(150, 150, 100, 150));

        // Calculate the spacing between cells based on gameSize
        int cellSpacing = 8 * sudoku.getGameSize() / 9;

        panel.setLayout(new GridLayout(sudoku.getGameSize(), sudoku.getGameSize(), cellSpacing, cellSpacing));
        panel.setBackground(Color.DARK_GRAY);

        if (cellButtons == null || cellButtons.length != sudoku.getGameSize()) {
            // If cellButtons is null or has different size, create a new array
            cellButtons = new Cell[sudoku.getGameSize()][sudoku.getGameSize()];
        } else {
            // Remove existing buttons from the panel
            for (int row = 0; row < sudoku.getGameSize(); row++) {
                for (int col = 0; col < sudoku.getGameSize(); col++) {
                    panel.remove(cellButtons[row][col]);
                }
            }
        }

        // Create new buttons and add them to the panel
        for (int row = 0; row < sudoku.getGameSize(); row++) {
            for (int col = 0; col < sudoku.getGameSize(); col++) {
                cellButtons[row][col] = new Cell(row, col, sudoku);
                panel.add(cellButtons[row][col]);
            }
        }
        return panel;
    }


    private JPanel buttonsPlane(){
        JPanel controlBtnPanel = new JPanel(new FlowLayout());
        // instance all the buttons
        saveBtn = new JButton("Save");
        undoBtn = new JButton("Undo");
        clearBtn = new JButton("Clear");
        loadBtn = new JButton("Load");
        quitBtn = new JButton("Quit");
        easyRadioBtn = new JRadioButton("Easy");
        hardRadioBtn = new JRadioButton("Hard");
        rulesBtn = new JButton("How to Play");


        // Add all buttons to the controlBtnPanel
        controlBtnPanel.add(saveBtn);
        controlBtnPanel.add(undoBtn);
        controlBtnPanel.add(clearBtn);
        controlBtnPanel.add(loadBtn);
        controlBtnPanel.add(quitBtn);
        controlBtnPanel.add(rulesBtn);



        ButtonCtrls buttonsCtrls = new ButtonCtrls(slot, parentFrame, cellButtons, sudoku);
        // add buttons with listerner to pop up menu
        rulesBtn.addActionListener(e -> showRules());
        quitBtn.addActionListener(e -> System.exit(0));
        loadBtn.addActionListener(buttonsCtrls);
        clearBtn.addActionListener(buttonsCtrls);
        undoBtn.addActionListener(buttonsCtrls);
        saveBtn.addActionListener(buttonsCtrls);

  

        return controlBtnPanel;
    }
    /**private void updateBoard(String level) {
        // Update the Sudoku board based on the selected level file
        sudoku = new Sudoku();
        // Notify the GUI to update the displayed board
        sudoku.addObserver(this);
        createPanel();
    
    */
    
      private void updateBoard() {
        createPanel();
    }



    //  a method to update a specific cell button
    private void updateCellButton(int row, int col, boolean moveResult) {
        if (moveResult) {
            // If the move was successful, update the cell button text
            cellButtons[row][col].setText(sudoku.getMoves()[row][col].getState());
        } else {
            // If the move failed, display an error message (if needed)
            JOptionPane.showMessageDialog(null, "Invalid move. Check how to play");
        }
    }



    private void showRules() {
        // New frame for rules display
        JFrame rulesFrame = new JFrame("How to play Sudoku!");
        JTextArea rulesTextArea = new JTextArea("The rules for sudoku are simple. A 9×9 square must be filled in"+
                " with numbers from 1-9 or a 4x4 square with numbers 1-4 with no repeated numbers in each line, horizontally or vertically."+
                " There are Also 3×3 squares within the grid, and each of these squares can't have any repeat numbers either. HAPPY SOLVING!");
        rulesTextArea.setEditable(false); // so rules area won't be editable
        rulesTextArea.setLineWrap(true); // Style wrapping for the long rules string type
        rulesTextArea.setWrapStyleWord(true); //  Proper indentation and line break

        JScrollPane scrollPane = new JScrollPane(rulesTextArea);
        rulesFrame.add(scrollPane);
        rulesFrame.setSize(400, 300);
        rulesFrame.setLocationRelativeTo(null); // Center the frame on the screen
        rulesFrame.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Sudoku && arg instanceof Assign) {
            Assign updatedAssign = (Assign) arg;
            int row = updatedAssign.getRow();
            int col = updatedAssign.getCol();
            if (cellButtons != null) {
                cellButtons[row][col].setText(updatedAssign.getState());
            }
        }
    }


    /**
     * An example of a method to display the GUI foR Sudoku
     *
     * @param args sample parameter for a method
     */
    public static void  main(String[] args) {
        //new GUI();}

        }
    }

