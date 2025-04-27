import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ScorePanel extends JPanel {
    private List<ScoreEntry> scoreEntries;
    private JPanel scoreListPanel;

    public ScorePanel() {
        this.scoreEntries = new ArrayList<>();
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Scoreboard");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.BLUE);
        add(title, BorderLayout.NORTH);

        scoreListPanel = new JPanel(new GridLayout(0, 1));
        scoreListPanel.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(scoreListPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateScores(List<ScoreEntry> updatedScores) {
        scoreEntries = updatedScores;
        scoreListPanel.removeAll();

        for (ScoreEntry entry : scoreEntries) {
            JPanel row = new JPanel(new GridLayout(1, 3));
            row.setOpaque(false);

            JLabel nameLabel = new JLabel(entry.getPlayerName());
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel scoreLabel = new JLabel(String.valueOf(entry.getScore()));
            scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel modeLabel = new JLabel(entry.getGameMode());
            modeLabel.setHorizontalAlignment(SwingConstants.CENTER);

            row.add(nameLabel);
            row.add(scoreLabel);
            row.add(modeLabel);

            scoreListPanel.add(row);
        }

        revalidate();
        repaint();
    }

    public void addScore(ScoreEntry newEntry) {
        scoreEntries.add(newEntry);
        updateScores(scoreEntries);
    }
}
