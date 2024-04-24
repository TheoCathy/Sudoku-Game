import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeGUI extends JFrame {
    private JRadioButton easyRadioBtn;
    private JRadioButton hardRadioBtn;
    private JButton startBtn;

    public HomeGUI() {
        setTitle("Welcome to My Sudoku!");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel headerLabel = new JLabel("Welcome to My Sudoku! Choose a difficulty to start the game ");
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        easyRadioBtn = new JRadioButton("Easy");
        hardRadioBtn = new JRadioButton("Hard");
        startBtn = new JButton("Start Sudoku!");

        mainPanel.add(easyRadioBtn, BorderLayout.WEST);
        mainPanel.add(hardRadioBtn, BorderLayout.CENTER);
        mainPanel.add(startBtn, BorderLayout.SOUTH);

        ButtonGroup levelBtnGroup = new ButtonGroup();
        levelBtnGroup.add(easyRadioBtn);
        levelBtnGroup.add(hardRadioBtn);

        startBtn.addActionListener(e -> startGame());

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startGame() {
        String selectedLevel = easyRadioBtn.isSelected() ? "easy" : "hard";
        dispose();
        new GUI(selectedLevel);
    }


public static void main(String[] args) {
         new HomeGUI();
    }

    }

