public class ScoreEntry {
    private String playerName;
    private int score;
    private String gameMode;

    public ScoreEntry(String playerName, int score, String gameMode) {
        this.playerName = playerName;
        this.score      = score;
        this.gameMode   = gameMode;
    }

    public String getPlayerName() { return playerName; }
    public int    getScore()      { return score;      }
    public String getGameMode()   { return gameMode;   }
}