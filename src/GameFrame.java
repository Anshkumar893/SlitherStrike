import java.awt.*; // Game window and UI components library
import javax.swing.*; // Layout management library

public class GameFrame extends JFrame { // GameFrame uses javax.swing to create a new window
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private MainMenuPanel mainMenu;
    private LevelSelectionPanel levelSelection;
    private DifficultySelectionPanel difficultySelection;
    private GamePanel gamePanel;  // GamePanel object
    private ScorePanel scorePanel;
    private int currentLevel = 1;
    private int currentDifficulty = 0;
    private int selectedLevel;

    public GameFrame() {
        setTitle("Slither Strike");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout(); // Used to switch between screens
        mainPanel = new JPanel(cardLayout); // Memory allocation for UI components

        // Initializing all panels
        mainMenu = new MainMenuPanel(this);
        levelSelection = new LevelSelectionPanel(this);
        difficultySelection = new DifficultySelectionPanel(this);
        gamePanel = new GamePanel(this);  // Initialize GamePanel
        scorePanel = new ScorePanel(this);

        // Adding the panels to the main screen with card layout
        mainPanel.add(mainMenu, "MENU");
        mainPanel.add(levelSelection, "LEVELS");
        mainPanel.add(difficultySelection, "DIFFICULTY");
        mainPanel.add(gamePanel, "GAME");
        mainPanel.add(scorePanel, "SCORES");

        add(mainPanel);
        showScreen("MENU");  // Show the main menu initially
    }

    // Screen switching functionality using CardLayout
    public void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
        if (screenName.equals("GAME")) {
            gamePanel.requestFocusInWindow();  // Ensure keyboard input goes to the game panel
        }
    }

    // Display the level selection screen
    public void showLevelScreen() {
        showScreen("LEVELS");
    }

    // Display the difficulty selection screen
    public void showDifficultyScreen(int level) {
        this.selectedLevel = level;  // Store the selected level
        difficultySelection.updateLevel(level);  // Update the difficulty panel with the selected level
        showScreen("DIFFICULTY");
    }

    // Start the game with the chosen level and difficulty
    public void startGame() {
        gamePanel.startGame(currentLevel, currentDifficulty);  // Start the game with the current level and difficulty
        showScreen("GAME");  // Show the game screen
    }

    // Start the game using the selected level and difficulty passed as parameters (over loading)
    public void startGame(int currentLevel, int currentDifficulty) {
        this.currentLevel = currentLevel;  // Set the current level
        this.currentDifficulty = currentDifficulty;  // Set the current difficulty
        gamePanel.startGame(currentLevel, currentDifficulty);  // Start the game with the provided parameters
        showScreen("GAME");  // Show the game screen
    }

    // Set the current difficulty level for the game
    public void setCurrentDifficulty(int difficulty) {
        this.currentDifficulty = difficulty;  // Set the difficulty
    }

    // Get the current selected level
    public int getCurrentLevel() {
        return currentLevel;  // Return the current level
    }

    // Get the current difficulty level
    public int getCurrentDifficulty() {
        return currentDifficulty;  // Return the current difficulty
    }

    // Getter method to access the GamePanel from other classes
    public GamePanel getGamePanel() {
        return gamePanel;  // Return the instance of GamePanel
    }
}
