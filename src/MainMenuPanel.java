import java.awt.*;
import javax.swing.*;

public class MainMenuPanel extends JPanel {
    public static final Color THEME_COLOR = new Color(70, 130, 180); // steel blue

    public MainMenuPanel(GameFrame frame) {
        // 1) Panel setup
        setBackground(THEME_COLOR);

        // 2) Use GridBagLayout to center everything
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;               // one column
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);  // spacing above/below each component

        // 3) Title
        JLabel title = new JLabel("Slither Strike");
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        gbc.gridy = 0;  // first row
        add(title, gbc);

        // 4) Row of buttons
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(makeButton("Play", () -> frame.showScreen("LEVELS")));
        buttonRow.add(makeButton("Scorecard", () -> frame.showScreen("SCORES")));
        buttonRow.add(makeButton("Exit", () -> System.exit(0)));

        gbc.gridy = 1;  // second row
        add(buttonRow, gbc);
    }

    // Helper to create a uniformly styled button
    private JButton makeButton(String text, Runnable action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 18));
        btn.setForeground(Color.WHITE);
        btn.setBackground(THEME_COLOR.darker());
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.addActionListener(e -> action.run());
        return btn;
    }
}
