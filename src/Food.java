import java.awt.*;
import java.util.Random;

public class Food {
    private int x, y;
    private String foodType; // Different types of food (e.g., "normal", "exploding")
    private static final int SIZE = 20; // Size of the food item
    
    // Constructor to create the food item
    public Food(String foodType) {
        this.foodType = foodType;
        spawnFood();
    }
    
    // Randomly spawn food in the game area (avoid obstacles and snake)
    public void spawnFood() {
        Random rand = new Random();
        this.x = rand.nextInt(30) * SIZE;  // Example: spawn at positions multiple of SIZE
        this.y = rand.nextInt(30) * SIZE;
        
        // Ensure food doesn't spawn on top of obstacles or the snake (implement as needed)
        // If there are any obstacles or snake body parts, re-spawn the food
        // This logic can be customized based on your game's world size and obstacle placement
    }
    
    // Method to draw the food on the game panel
    public void draw(Graphics g) {
        if (foodType.equals("exploding")) {
            g.setColor(Color.RED);  // Exploding food can be a different color
        } else {
            g.setColor(Color.GREEN);  // Normal food is green
        }
        g.fillRect(x, y, SIZE, SIZE);  // Draw food as a rectangle, can use images if you prefer
    }

    // Method to check if the snake has eaten the food
    public boolean checkCollision(int snakeX, int snakeY) {
        return (snakeX == this.x && snakeY == this.y);
    }
    
    // Getters and setters for the position and type of the food
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
}
