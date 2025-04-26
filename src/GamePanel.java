import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
    private static final int TILE_SIZE = 20;  // Size of each tile (snake and food)
    private static final int WIDTH = 800;     // Width of the game area
    private static final int HEIGHT = 600;    // Height of the game area
    private boolean snakeInvisible = false;  // Flag to track if the snake is invisible

    private LinkedList<Point> snake;         // Linked list to store the snake's body
    private Point food;                      // Point to store the current food position
    private Point direction;                 // Direction in which the snake is moving
    private Point nextDirection;             // Next direction for the snake movement
    private Timer gameTimer;                 // Timer for the game loop (javax.swing.Timer)
    private Random random;                   // Random number generator for food and obstacle placement
    private boolean isRunning;               // Flag to check if the game is running
    private int score;                       // Current score of the game
    private int currentLevel;                // Current level of the game
    private int currentDifficulty;           // Current difficulty level
    private GameFrame frame;                 // Game frame reference
    private ArrayList<Point> obstacles;      // List to store obstacle positions

    private Timer invisibilityTimer;         // Timer for invisibility mode (javax.swing.Timer)
    private boolean isInvisible = false;     // Flag to toggle invisibility every 15 seconds

    public GamePanel(GameFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));  // Set panel size
        setBackground(Color.BLACK);  // Set background color
        setFocusable(true);  // Enable panel to receive keyboard input

        snake = new LinkedList<>();  // Initialize the snake's body as a linked list
        direction = new Point(TILE_SIZE, 0);  // Initially, the snake will move to the right
        nextDirection = new Point(TILE_SIZE, 0);  // The next direction for movement is also to the right
        random = new Random();  // Random number generator for food and obstacles
        obstacles = new ArrayList<>();  // List to store obstacle points
        isRunning = false;  // Game is initially not running
        score = 0;  // Initialize score to 0

        addKeyListener(new GameKeyListener());  // Add a key listener for controlling the snake

        // Initialize Timer for the invisibility mode (javax.swing.Timer)
        invisibilityTimer = new Timer(15000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle invisibility every 15 seconds
                isInvisible = !isInvisible;
                setSnakeInvisible(isInvisible);  // Call the method to toggle snake visibility

                // Log the invisibility toggle for debugging
                if (isInvisible) {
                    System.out.println("Snake is now invisible!");
                } else {
                    System.out.println("Snake is visible again.");
                }
            }
        });
        invisibilityTimer.setInitialDelay(0);  // Set initial delay to 0 for immediate start
    }

    // Start the game with chosen level and difficulty
    public void startGame(int level, int difficulty) {
        this.currentLevel = level;  // Set the current level
        this.currentDifficulty = difficulty;  // Set the current difficulty
        initGame();  // Initialize the game state

        // Adjust speed based on the level and difficulty
        int speed = 200 - (level * 10) - (difficulty * 30);
        int obstacleCount = level + (difficulty * 2);

        // Stop any existing game timer
        if (gameTimer != null) {
            gameTimer.stop();  // Stop the existing game timer
        }

        // Create and start a new game timer (javax.swing.Timer)
        gameTimer = new Timer(speed, this);  // 'this' refers to ActionListener (GamePanel)
        gameTimer.start();  // Start the timer

        isRunning = true;  // Set the game as running
        requestFocusInWindow();  // Request focus for the panel to receive keyboard inputs

        // Start the invisibility timer to toggle invisibility every 15 seconds
        invisibilityTimer.start();
    }

    // Initialize or restart the game
    private void initGame() {
        snake.clear();  // Clear previous snake data
        obstacles.clear();  // Clear obstacles
        snake.add(new Point(WIDTH / 2, HEIGHT / 2));  // Set the snake's starting position at the center
        spawnFood();  // Spawn the initial food
        direction.setLocation(TILE_SIZE, 0);  // Set initial movement direction (right)
        nextDirection.setLocation(TILE_SIZE, 0);  // Set the next direction to right as well
        score = 0;  // Reset the score
        isRunning = true;  // Set game to running state
    }

    // Generate random obstacles in the game area
    private void generateObstacles(int count) {
        obstacles.clear();  // Clear previous obstacles
        for (int i = 0; i < count; i++) {
            int x, y;
            do {
                // Generate random x and y positions for obstacles
                x = random.nextInt(WIDTH / TILE_SIZE) * TILE_SIZE;
                y = random.nextInt(HEIGHT / TILE_SIZE) * TILE_SIZE;
            } while (snake.contains(new Point(x, y)) || (food != null && x == food.x && y == food.y));  // Ensure obstacles don't overlap snake or food

            obstacles.add(new Point(x, y));  // Add the new obstacle to the list
        }
    }

    // Spawn food at a random location on the screen
    private void spawnFood() {
        int x, y;
        do {
            // Generate random x and y positions for the food
            x = random.nextInt(WIDTH / TILE_SIZE) * TILE_SIZE;
            y = random.nextInt(HEIGHT / TILE_SIZE) * TILE_SIZE;
        } while (snake.contains(new Point(x, y)) || isObstacle(x, y));  // Ensure food doesn't overlap snake or obstacles

        food = new Point(x, y);  // Set the food position
    }

    // Check if a given position is occupied by an obstacle
    private boolean isObstacle(int x, int y) {
        for (Point obs : obstacles) {
            if (obs.x == x && obs.y == y) return true;  // If position matches an obstacle, return true
        }
        return false;  // Return false if no obstacle at the given position
    }

    // Set the snake to be invisible or visible
    public void setSnakeInvisible(boolean invisible) {
        this.snakeInvisible = invisible;  // Set the snakeInvisible flag
        repaint();  // Redraw the screen to apply the invisibility change
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!isRunning) {
            g.setColor(Color.RED);  // Set the color for game over message
            g.setFont(new Font("Arial", Font.BOLD, 48));  // Set font for game over message
            g.drawString("GAME OVER", WIDTH / 2 - 150, HEIGHT / 2);  // Draw game over text
            g.setFont(new Font("Arial", Font.PLAIN, 24));  // Set font for restart instructions
            g.drawString("Press SPACE to restart", WIDTH / 2 - 100, HEIGHT / 2 + 40);  // Instructions to restart
            return;
        }

        // Draw obstacles
        g.setColor(new Color(139, 69, 19));  // Set obstacle color to brown
        for (Point obs : obstacles) {
            g.fillRect(obs.x, obs.y, TILE_SIZE, TILE_SIZE);  // Draw each obstacle
        }

        // Draw food
        g.setColor(Color.WHITE);
        g.fillRect(food.x, food.y, TILE_SIZE, TILE_SIZE);  // Draw food

        // Draw snake based on visibility flag
        if (!snakeInvisible) {  // If snake is visible, draw entire snake
            g.setColor(Color.GREEN);  // Set snake color to green
            for (Point p : snake) {
                g.fillRect(p.x, p.y, TILE_SIZE, TILE_SIZE);  // Draw each body part of the snake
            }
        } else {  // If snake is invisible, only draw the head
            Point head = snake.get(0);  // Get the snake's head
            g.setColor(Color.GREEN);  // Set color to green for the head
            g.fillRect(head.x, head.y, TILE_SIZE, TILE_SIZE);  // Draw only the head
        }

        // Draw the snake's head in dark green
        if (!snake.isEmpty()) {
            g.setColor(new Color(0, 100, 0));  // Dark green for head
            g.fillRect(snake.getFirst().x, snake.getFirst().y, TILE_SIZE, TILE_SIZE);  // Draw head
        }

        // Display score, level, and difficulty
        g.setColor(Color.WHITE);  // Set text color to white
        g.setFont(new Font("Arial", Font.PLAIN, 20));  // Set font for score, level, etc.
        g.drawString("Score: " + score, 10, 20);  // Draw score
        g.drawString("Level: " + currentLevel, 10, 40);  // Draw level
        g.drawString("Difficulty: " + getDifficultyName(), 10, 60);  // Draw difficulty level
        g.drawString("ESC: Menu", 10, HEIGHT - 20);  // Draw escape key instruction
    }

    // Convert difficulty integer to string
    private String getDifficultyName() {
        switch (currentDifficulty) {
            case 0: return "Easy";
            case 1: return "Medium";
            case 2: return "Hard";
            default: return "";
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isRunning) return;

        // Update direction based on key input
        direction.setLocation(nextDirection);

        // Snake movement
        Point head = snake.getFirst();
        Point newHead = new Point(head.x + direction.x, head.y + direction.y);

        // Wrap the snake around if it goes out of bounds
        if (newHead.x < 0) newHead.x = WIDTH - TILE_SIZE;
        if (newHead.x >= WIDTH) newHead.x = 0;
        if (newHead.y < 0) newHead.y = HEIGHT - TILE_SIZE;
        if (newHead.y >= HEIGHT) newHead.y = 0;

        // Collision detection (self and obstacles)
        if (snake.contains(newHead) || isObstacle(newHead.x, newHead.y)) {
            gameOver();  // End game if collision occurs
            return;
        }

        snake.addFirst(newHead);  // Add the new head

        // Check if the snake eats food
        if (newHead.equals(food)) {
            score += 10 * (currentLevel + currentDifficulty + 1);  // Increase score
            spawnFood();  // Spawn new food
        } else {
            snake.removeLast();  // Remove the tail if no food is eaten
        }

        repaint();  // Redraw the game screen
    }

    private void gameOver() {
        isRunning = false;  // Set the game to stopped state
        gameTimer.stop();  // Stop the game timer
        repaint();  // Redraw to show game over message
    }

    // Key listener for controlling the snake
    private class GameKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (direction.y == 0) nextDirection.setLocation(0, -TILE_SIZE);  // Move up
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if (direction.y == 0) nextDirection.setLocation(0, TILE_SIZE);  // Move down
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (direction.x == 0) nextDirection.setLocation(-TILE_SIZE, 0);  // Move left
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (direction.x == 0) nextDirection.setLocation(TILE_SIZE, 0);  // Move right
                    break;
                case KeyEvent.VK_ESCAPE:
                    frame.showScreen("MENU");  // Show menu on Escape
                    break;
                case KeyEvent.VK_SPACE:
                    if (!isRunning) startGame(currentLevel, currentDifficulty);  // Start/restart game
                    break;
            }
        }
    }
}
