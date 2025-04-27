import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;
    private MainMenuPanel mainMenuPanel;
    private LevelSelectionPanel levelSelectionPanel;
    private DifficultySelectionPanel difficultySelectionPanel;
    
    public GameFrame() {
        // Initialize the panels
        gamePanel = new GamePanel();
        mainMenuPanel = new MainMenuPanel(this);
        levelSelectionPanel = new LevelSelectionPanel(this);
        difficultySelectionPanel = new DifficultySelectionPanel(this);

        // Set the layout manager to CardLayout, so we can switch between panels
        setLayout(new CardLayout());

        // Add panels to the card layout
        add(mainMenuPanel, "MAIN_MENU");
        add(levelSelectionPanel, "LEVEL_SELECTION");
        add(difficultySelectionPanel, "DIFFICULTY_SELECTION");
        add(gamePanel, "GAME_PANEL");

        // Set up frame properties
        setTitle("Snake Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Show the main menu by default
        showScreen("MAIN_MENU");
    }

    // Method to show a specific screen based on its name
    public void showScreen(String screenName) {
        CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), screenName);
    }

    // This method will be called when a level is selected
    public void onLevelSelected(Level selectedLevel) {
        // Switch to difficulty selection screen
        showScreen("DIFFICULTY_SELECTION");

        // Set listener for difficulty selection
        difficultySelectionPanel.setOnDifficultySelectedListener(e -> {
            // Get selected difficulty and start the game
            Difficulty selectedDifficulty = Difficulty.valueOf(e.getActionCommand());
            startSelectedGame(selectedLevel, selectedDifficulty);
        });
    }

    // This method will be called to start the game with the selected level and difficulty
    public void startSelectedGame(Level selectedLevel, Difficulty selectedDifficulty) {
        gamePanel.startGame(selectedLevel, selectedDifficulty);
        showScreen("GAME_PANEL");
    }
}
