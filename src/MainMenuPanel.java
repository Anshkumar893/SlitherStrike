import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    public static final Color THEME_COLOR = new Color(70, 130, 180);

    public MainMenuPanel(GameFrame frame) {
        setBackground(THEME_COLOR);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel title = new JLabel("Slither Strike");
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        gbc.gridy = 0;
        add(title, gbc);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(makeButton("Play", () -> frame.showScreen("LEVEL_SELECTION")));
        buttonRow.add(makeButton("Exit", () -> System.exit(0)));

        gbc.gridy = 1;
        add(buttonRow, gbc);
    }

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
