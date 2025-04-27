// Difficulty.java - Enum to manage different difficulty levels
public enum Difficulty {
    EASY(5, 3),
    MEDIUM(7, 5),
    HARD(10, 7);

    private final int snakeSpeed;
    private final int obstacles;

    Difficulty(int snakeSpeed, int obstacles) {
        this.snakeSpeed = snakeSpeed;
        this.obstacles = obstacles;
    }

    public int getSnakeSpeed() {
        return snakeSpeed;
    }

    public int getObstacles() {
        return obstacles;
    }
}
