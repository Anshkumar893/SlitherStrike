import java.awt.*;
import java.util.Random;

public class Obstacle {
    private int x, y;
    private static final int SIZE = 20; // Size of the obstacle
    
    // Constructor to create an obstacle at random location
    public Obstacle() {
        spawnObstacle();
    }
    
    // Randomly spawn obstacles in the game area
    public void spawnObstacle() {
        Random rand = new Random();
        this.x = rand.nextInt(30) * SIZE;  // Example: spawn at positions multiple of SIZE
        this.y = rand.nextInt(30) * SIZE;
        
        // Ensure obstacle doesn't spawn on top of the snake or food (implement as needed)
        // You can add logic here to prevent spawning obstacles in places occupied by the snake or food
    }
    
    // Method to draw the obstacle on the game panel
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);  // Obstacles are black
        g.fillRect(x, y, SIZE, SIZE);  // Draw obstacle as a rectangle, can use images if you prefer
    }

    // Method to check if the snake has hit an obstacle
    public boolean checkCollision(int snakeX, int snakeY) {
        return (snakeX == this.x && snakeY == this.y);
    }

    // Getters and setters for the position of the obstacle
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
