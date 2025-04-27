// Level.java - Base class for all levels
public abstract class Level {
    protected int levelNumber;
    protected Difficulty difficulty;

    // Abstract method to apply specific rules for each level
    public abstract void applyLevelRules(GamePanel gamePanel);

    // Getter for difficulty
    public Difficulty getDifficulty() {
        return difficulty;
    }

    // Setter for difficulty
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
