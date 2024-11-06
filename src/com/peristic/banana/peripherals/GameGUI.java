package com.peristic.banana.peripherals;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import com.perisic.banana.engine.GameEngine;

/**
 * A Beautiful Graphical User Interface for the Six Equation Game.
 */
public class GameGUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = -107785653906635L;
    private int wrongAttempts = 0;
    private static final int MAX_WRONG_ATTEMPTS = 3;

    private static final String[] correctMessages = {
        "Wow! You smashed it!",
        "Great job! Keep going!",
        "Correct! You're on fire!",
        "Fantastic! You got it right!",
        "Awesome! Nailed it!"
    };

    private static final String[] wrongMessages = {
        "Oops, not quite right. Try again!",
        "No worries! You’ll get the next one.",
        "Incorrect! Don’t give up!",
        "Not the right answer! Keep trying!",
        "Wrong, but don’t lose hope!"
    };

    JLabel questArea = null;
    JLabel playerNameLabel = null;
    JLabel highScoreLabel = null;
    GameEngine myGame = null;
    BufferedImage currentGame = null;
    JTextArea infoArea = null;
    RoundedButton[] answerButtons = new RoundedButton[10];

    private String username;
    private int currentScore = 0;
    private int userHighScore = 0;

    LoginData ldata = new LoginData();

    /**
     * Initializes the game with improved UI elements.
     */
    private void initGame(String player) {
        this.username = player;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("What is the missing value?");
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(30, 30, 30)); // Dark theme background
        panel.setBorder(new EmptyBorder(15, 15, 15, 15)); // Padding around the content

        myGame = new GameEngine(player);
        currentGame = myGame.nextGame();

        // Top Panel with Player Info
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.setBackground(new Color(50, 50, 50));
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.DARK_GRAY));

        playerNameLabel = createStyledLabel("Player: " + player);
        highScoreLabel = createStyledLabel("High Score: " + userHighScore);

        topPanel.add(playerNameLabel);
        topPanel.add(highScoreLabel);
        panel.add(topPanel, BorderLayout.NORTH);

        // Information Area
        infoArea = new JTextArea(1, 40);
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 16));
        infoArea.setForeground(Color.WHITE);
        infoArea.setBackground(new Color(60, 60, 60));
        infoArea.setText("What is the value of the tomato?   Score: 0");
        panel.add(infoArea, BorderLayout.SOUTH);

        // Center Panel with the Game Image
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(30, 30, 30));

        questArea = new JLabel(new ImageIcon(currentGame));
        centerPanel.add(questArea, BorderLayout.CENTER);
        panel.add(centerPanel, BorderLayout.CENTER);

        // Button Panel with Answer Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        buttonPanel.setBackground(new Color(40, 40, 40));
        for (int i = 0; i < 10; i++) {
            answerButtons[i] = new RoundedButton(String.valueOf(i));
            buttonPanel.add(answerButtons[i]);
            answerButtons[i].addActionListener(this);
        }

        panel.add(buttonPanel, BorderLayout.SOUTH);

        pack();
        getContentPane().add(panel);
        setSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));
    }

    /**
     * Creates a styled label for the UI.
     */
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(173, 216, 230)); // Light Blue
        return label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int solution = Integer.parseInt(e.getActionCommand());
        boolean correct = myGame.checkSolution(solution);
        currentScore = myGame.getScore();

        if (correct) {
            wrongAttempts = 0;
            currentGame = myGame.nextGame();
            questArea.setIcon(new ImageIcon(currentGame));
            infoArea.setText("Good!  Score: " + currentScore);

            if (currentScore > userHighScore) {
                userHighScore = currentScore;
                highScoreLabel.setText("High Score: " + userHighScore);
                ldata.updateHighScore(username, userHighScore);
                showMessage("New High Score: " + userHighScore + "!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
            } else {
                showMessage(randomMessage(correctMessages), "Congratulations", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            wrongAttempts++;
            showMessage(randomMessage(wrongMessages), "Oops!", JOptionPane.ERROR_MESSAGE);
            infoArea.setText("Oops. Try again!  Score: " + currentScore);

            if (wrongAttempts >= MAX_WRONG_ATTEMPTS) {
                endGame();
            }
        }
    }

    private String randomMessage(String[] messages) {
        int randomIndex = ThreadLocalRandom.current().nextInt(messages.length);
        return messages[randomIndex];
    }

    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private void endGame() {
        for (RoundedButton btn : answerButtons) {
            btn.setEnabled(false);
        }

        int option = JOptionPane.showOptionDialog(this,
            "Game Over! Your Score: " + currentScore + "\nHigh Score: " + userHighScore + "\nWould you like to play again?",
            "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
            null, new Object[] { "Replay", "Exit" }, "Replay");

        if (option == JOptionPane.YES_OPTION) {
            replayGame();
        } else {
            System.exit(0);
        }
    }

    private void replayGame() {
        wrongAttempts = 0;
        currentScore = 0;
        infoArea.setText("What is the value of the tomato?   Score: 0");
        myGame = new GameEngine(username);
        currentGame = myGame.nextGame();
        questArea.setIcon(new ImageIcon(currentGame));
        for (RoundedButton btn : answerButtons) {
            btn.setEnabled(true);
        }
    }

    public GameGUI(String player) {
        super();
        initGame(player);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GameGUI("Guest").setVisible(true);
        });
    }
}

/**
 * A custom JButton with rounded corners and hover effects.
 */
class RoundedButton extends JButton {
    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setFont(new Font("Arial", Font.BOLD, 18));
        setForeground(Color.WHITE);
        setBackground(new Color(72, 61, 139));
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(123, 104, 238));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(new Color(72, 61, 139));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        super.paintComponent(g);
    }
}
