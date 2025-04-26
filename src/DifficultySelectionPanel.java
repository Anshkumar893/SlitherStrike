import java.awt.*; // Game window aur UI components ki library
import javax.swing.*; // Layout management ki library

public class DifficultySelectionPanel extends JPanel {
    private JLabel levelLabel;

    public DifficultySelectionPanel(GameFrame frame) {
        setLayout(new BorderLayout());
        setBackground(new Color(70, 130, 180));

        levelLabel = new JLabel("", JLabel.CENTER);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 24));
        levelLabel.setForeground(Color.WHITE);
        add(levelLabel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonsPanel.setOpaque(false);

        String[] difficulties = {"Easy", "Medium", "Hard"};
        Color[] colors = {Color.GREEN, Color.ORANGE, Color.RED};

        for (int i = 0; i < 3; i++) {
            JButton btn = new JButton(difficulties[i]);
            btn.setBackground(colors[i]);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            final int difficulty = i;
            btn.addActionListener(e -> {
                frame.setCurrentDifficulty(difficulty);
                frame.startGame();
            });
            buttonsPanel.add(btn);
        }

        add(buttonsPanel, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back to Levels");
        backBtn.addActionListener(e -> frame.showLevelScreen());
        add(backBtn, BorderLayout.SOUTH);
    }

    public void updateLevel(int level) {
        levelLabel.setText("Level " + level + " - Select Difficulty");
    }
}