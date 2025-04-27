import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private List<Obstacle> obstacles;
    private List<Food> foodItems;

    private int snakeX, snakeY; // Snake's current position
    private int snakeSpeed;
    private int snakeLength; // Snake length
    private List<Point> snakeBody; // To track the snake's body (for collision detection)

    private boolean gameOver;

    private Level currentLevel; // Current level being played
    private Difficulty currentDifficulty; // Current difficulty

    public GamePanel() {
        this.obstacles = new ArrayList<>();
        this.foodItems = new ArrayList<>();
        this.snakeBody = new ArrayList<>();
        this.gameOver = false;

        // Set up key listener for movement control
        setKeyListener();
    }

    // Start the game with the selected level and difficulty
    public void startGame(Level level, Difficulty difficulty) {
        // Reset game state
        this.snakeX = 100;
        this.snakeY = 100;
        this.snakeSpeed = difficulty.getSnakeSpeed();
        this.snakeLength = 1;
        this.snakeBody.clear();
        this.snakeBody.add(new Point(snakeX, snakeY));
        this.gameOver = false;

        // Set the current level and difficulty
        this.currentLevel = level;
        this.currentDifficulty = difficulty;

        // Apply level rules for obstacles, food, and speed
        level.applyLevelRules(this);

        // Repaint the game panel to display the initial game state
        repaint();
    }

    // Spawn obstacles based on the difficulty
    public void setObstacles(int count) {
        obstacles.clear(); // Clear existing obstacles
        for (int i = 0; i < count; i++) {
            // Spawn an obstacle
            Obstacle obstacle = new Obstacle();
            obstacles.add(obstacle);
        }
    }

    // Set snake speed based on difficulty
    public void setSnakeSpeed(int speed) {
        this.snakeSpeed = speed;
        // You can use this to adjust the speed of the snake in your game logic
    }

    // Spawn food items based on the difficulty and level
    public void setFoodItems() {
        foodItems.clear(); // Clear existing food items
        for (int i = 0; i < 5; i++) {  // Example: spawn 5 food items
            Food food = new Food("normal"); // Example: spawn normal food
            foodItems.add(food);
        }
    }

    // Set up key listener for snake movement
    private void setKeyListener() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameOver) {
                    return;  // Don't allow movement when the game is over
                }
                // Movement keys
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        moveSnake(0, -snakeSpeed);
                        break;
                    case KeyEvent.VK_DOWN:
                        moveSnake(0, snakeSpeed);
                        break;
                    case KeyEvent.VK_LEFT:
                        moveSnake(-snakeSpeed, 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveSnake(snakeSpeed, 0);
                        break;
                    case KeyEvent.VK_R: // Restart game if game over
                        if (gameOver) {
                            restartGame();
                        }
                        break;
                }
            }
        });
        this.setFocusable(true);  // Enable key events
    }

    // Move the snake
    public void moveSnake(int dx, int dy) {
        if (gameOver) {
            return;
        }
        // Move the snake's head
        int newHeadX = snakeX + dx;
        int newHeadY = snakeY + dy;

        // Move the snake body (update the body)
        snakeBody.add(0, new Point(newHeadX, newHeadY));
        snakeX = newHeadX;
        snakeY = newHeadY;

        // Check for collisions with food
        checkFoodCollision();

        // Check for collisions with obstacles or itself
        checkCollisions();

        // Keep the snake length the same (unless it eats food, then it grows)
        if (snakeBody.size() > snakeLength) {
            snakeBody.remove(snakeBody.size() - 1); // Remove the last part of the body (tail)
        }

        repaint(); // Redraw the game panel
    }

    // Check if the snake has eaten food
    private void checkFoodCollision() {
        for (Food food : foodItems) {
            if (food.checkCollision(snakeX, snakeY)) {
                // Snake eats the food, so respawn food and grow the snake
                food.spawnFood();
                snakeLength++;  // Increase snake length after eating food
            }
        }
    }

    // Check for collisions with obstacles or the snake itself
    private void checkCollisions() {
        // Check for collisions with obstacles
        for (Obstacle obstacle : obstacles) {
            if (obstacle.checkCollision(snakeX, snakeY)) {
                // Handle collision (e.g., game over)
                gameOver = true;
                System.out.println("Game Over! Snake hit an obstacle.");
            }
        }

        // Check for collisions with the snake itself
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeX == snakeBody.get(i).x && snakeY == snakeBody.get(i).y) {
                // Snake collided with itself
                gameOver = true;
                System.out.println("Game Over! Snake collided with itself.");
            }
        }
    }

    // Restart the game
    private void restartGame() {
        startGame(currentLevel, currentDifficulty); // Restart with the same level and difficulty
    }

    // Method to draw obstacles, food, and snake on the game panel
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw obstacles
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g);
        }

        // Draw food
        for (Food food : foodItems) {
            food.draw(g);
        }

        // Draw the snake
        g.setColor(Color.GREEN);
        for (Point point : snakeBody) {
            g.fillRect(point.x, point.y, 20, 20);  // Draw snake body
        }

        // Game Over text
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", 150, 200);
            g.drawString("Press R to Restart", 130, 250);
        }
    }
}
