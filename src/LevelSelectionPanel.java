import javax.swing.*;
import java.awt.*;


public class LevelSelectionPanel extends JPanel {
    private GameFrame gameFrame;

    public LevelSelectionPanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel title = new JLabel("Select Level");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.BLACK);
        gbc.gridy = 0;
        add(title, gbc);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonRow.setOpaque(false);

        // Fixing the lambda expressions to properly call onLevelSelected with the correct parameters
        buttonRow.add(makeButton("Level 1", () -> gameFrame.onLevelSelected(new FreePlayLevel1(Difficulty.MEDIUM))));
        buttonRow.add(makeButton("Level 2", () -> gameFrame.onLevelSelected(new FreePlayLevel2(Difficulty.MEDIUM))));
        buttonRow.add(makeButton("Level 3", () -> gameFrame.onLevelSelected(new FreePlayLevel3(Difficulty.MEDIUM))));
        buttonRow.add(makeButton("Level 4", () -> gameFrame.onLevelSelected(new FreePlayLevel4(Difficulty.MEDIUM))));

        gbc.gridy = 1;
        add(buttonRow, gbc);
    }

    private JButton makeButton(String text, Runnable action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 18));
        btn.setForeground(Color.BLACK);
        btn.setBackground(Color.LIGHT_GRAY);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.addActionListener(e -> action.run());
        return btn;
    }
}
