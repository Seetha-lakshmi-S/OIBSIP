import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGame extends JFrame {
    private int totalAttempts = 0;
    private int round = 1;
    private int attempt = 0;
    private int score = 0;
    private final int maxAttempts = 5;
    private final int maxRounds = 3;
    private int randomNumber;
    private JTextField guessField;
    private JLabel resultLabel;
    private JLabel roundLabel;
    private JLabel scoreLabel;
    private JLabel attemptLabel;
    private JButton checkButton;
    private boolean anyRoundGuessedCorrectly = false;

    public NumberGuessingGame() {
        setTitle("Number Guessing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);

        // Create a panel to set as the content pane
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("guess.jpeg");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);
        setContentPane(panel); // Set the panel as the content pane

        // Initialize components
        Font font = new Font("Arial", Font.BOLD, 20);
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);
        Color darkblack = new Color(20,20,20); // RGB: 20, 20, 20

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xPos = (screenSize.width - getWidth()) / 2;
        int yPos = (screenSize.height - getHeight()) / 2;
        setLocation(xPos, yPos);

        roundLabel = new JLabel("Round: " + round + "/" + maxRounds);
        roundLabel.setBounds(250, 135, 120, 25);
        roundLabel.setForeground(darkblack);
        roundLabel.setOpaque(true);
        roundLabel.setBackground(Color.white);
        add(roundLabel);

        attemptLabel = new JLabel();
        attemptLabel.setBounds(250, 160, 120, 25);
        attemptLabel.setOpaque(true);
        attemptLabel.setBackground(Color.white);
        attempt = 0;
        updateAttemptLabel();
        attemptLabel.setForeground(darkblack);
        add(attemptLabel);

        JLabel guessLabel = new JLabel("Guess the number between 1 to 100:");
        guessLabel.setFont(font);
        guessLabel.setBounds(100, 200, 345, 30);
        guessLabel.setForeground(darkblack);
        guessLabel.setOpaque(true);
        guessLabel.setBackground(Color.white);
        add(guessLabel);

        guessField = new JTextField(3);
        guessField.setFont(font);
        guessField.setBounds(453, 200, 50, 29);
        add(guessField);

        checkButton = new JButton("Check");
        checkButton.setFont(font);
        checkButton.setBounds(260, 250, 100, 30);
        add(checkButton);

        resultLabel = new JLabel();
        resultLabel.setFont(font);
        resultLabel.setBounds(10, 140, 400, 30);
        add(resultLabel);

        scoreLabel = new JLabel("Your Score: 0");
        scoreLabel.setFont(font);
        scoreLabel.setBounds(255, 300, 132, 30);
        scoreLabel.setForeground(darkblack);
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(Color.white);
        add(scoreLabel);

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int guess = 0;
                try {
                    guess = Integer.parseInt(guessField.getText());
                    if (guess < 1 || guess > 100) {
                        throw new NumberFormatException();
                    }
                    checkGuess(guess);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number between 1 and 100.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        startGame(); // Start the game
        setVisible(true);
    }

    private void startGame() {
        totalAttempts = 0;
        round = 1;
        score = 0;
        anyRoundGuessedCorrectly = false;
        nextRound();
    }

    private void nextRound() {
        if (round <= maxRounds) {
            Random random = new Random();
            randomNumber = random.nextInt(100) + 1;
            roundLabel.setText("Round: " + round + "/" + maxRounds);
            guessField.setText("");
            resultLabel.setText("");
            totalAttempts = 0;
            attempt = 0;
            updateAttemptLabel();
        } else {
            displayFinalMessage(); // Call displayFinalMessage when all rounds are completed
            dispose(); // Close the window after displaying the final message
        }
    }

    private void updateAttemptLabel() {
        attemptLabel.setText("Attempt: " + attempt + "/" + maxAttempts);
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Your Score: " + score);
    }

    private void checkGuess(int guess) {
        totalAttempts++;
        attempt++;
        updateAttemptLabel();
        if (guess == randomNumber) {
            anyRoundGuessedCorrectly = true;
            score++;
            updateScoreLabel();
            JOptionPane.showMessageDialog(null, "Congratulations! You guessed the number in " + totalAttempts + " attempt", "Correct Guess!", JOptionPane.PLAIN_MESSAGE);
        } else if (totalAttempts >= maxAttempts) {
            JOptionPane.showMessageDialog(null, "You have reached the maximum number of attempts.\n The correct number was: " + randomNumber, "Attempts Exceeded", JOptionPane.PLAIN_MESSAGE);
        } else if (guess < randomNumber) {
            JOptionPane.showMessageDialog(null, "Too low! Try again.", "Hint", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Too high! Try again.", "Hint", JOptionPane.INFORMATION_MESSAGE);
        }

        if (totalAttempts >= maxAttempts || guess == randomNumber) {
            if (round < maxRounds) {
                int choice = JOptionPane.showConfirmDialog(null, "Do you want to play another round?", "Play Again?", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    round++;
                    nextRound();
                } else {
                    if (anyRoundGuessedCorrectly) {
                        JOptionPane.showMessageDialog(null, "Thanks for playing! Your score: " + score, "Game Over", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Thanks for playing! Better luck next time!", "Game Over", JOptionPane.PLAIN_MESSAGE);
                    }
                    dispose();
                }
            } else {
                displayFinalMessage(); 
                dispose(); 
            }
        }
    }

    private void displayFinalMessage() {
        if (anyRoundGuessedCorrectly) {
            JOptionPane.showMessageDialog(null, "Thanks for playing all three rounds!\nYour Total score: " + score, "Game Over", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Thanks for playing all three rounds! Better luck next time!", "Game Over", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NumberGuessingGame();
            }
        });
    }
}
