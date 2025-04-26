import java.awt.*; // Game window aur UI components ki library
import javax.swing.*; // Layout management ki library

public class ScorePanel extends JPanel {
    public ScorePanel(GameFrame frame) {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 228, 181));

        JLabel title = new JLabel("High Scores", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JTextArea scoresArea = new JTextArea();
        scoresArea.setEditable(false);
        scoresArea.setFont(new Font("Arial", Font.PLAIN, 18));
        scoresArea.setText("1. Player1 - 1000\n2. Player2 - 800\n3. Player3 - 600"); // hard code kiya hua just for demonstration

        JScrollPane scrollPane = new JScrollPane(scoresArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back to Menu");
        backBtn.addActionListener(e -> frame.showScreen("MENU"));
        add(backBtn, BorderLayout.SOUTH);
    }
}