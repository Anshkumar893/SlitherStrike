import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class LevelSelectionPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel containerPane;

    public LevelSelectionPanel(GameFrame frame) {
        setLayout(new BorderLayout());
        setBackground(MainMenuPanel.THEME_COLOR);

        // 1) Container for the in-panel cards
        cardLayout = new CardLayout();
        containerPane = new JPanel(cardLayout);
        containerPane.setOpaque(false);

        // 2) Main menu inside this panel (Free Play / Special Levels)
        JPanel mainMenuPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        mainMenuPanel.setOpaque(false);

        // Smaller Free Play button
        JButton freePlayBtn = new JButton("Free Play");
        freePlayBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        freePlayBtn.setMaximumSize(new Dimension(30, 15));
        freePlayBtn.addActionListener(e -> cardLayout.show(containerPane, "FREE_PLAY"));
        mainMenuPanel.add(freePlayBtn);

        // Smaller Special Levels button
        JButton specialBtn = new JButton("Special Levels");
        specialBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        specialBtn.setPreferredSize(new Dimension(30, 15));
        specialBtn.addActionListener(e -> cardLayout.show(containerPane, "SPECIAL_LEVELS"));
        mainMenuPanel.add(specialBtn);

        // 3) Free Play submenu
        JPanel freePlayPanel = createFreePlayMenu(frame);
        // Back to in-panel MAIN
        JButton backFromFree = new JButton("Back");
        backFromFree.setFont(new Font("Arial", Font.PLAIN, 12));
        backFromFree.setPreferredSize(new Dimension(100, 30));
        backFromFree.addActionListener(e -> cardLayout.show(containerPane, "MAIN_MENU"));
        freePlayPanel.add(backFromFree);

        // 4) Special Levels submenu
        JPanel specialPanel = createSpecialLevelsMenu(frame);
        // Back to in-panel MAIN
        JButton backFromSpecial = new JButton("Back");
        backFromSpecial.setFont(new Font("Arial", Font.PLAIN, 12));
        backFromSpecial.setPreferredSize(new Dimension(100, 30));
        backFromSpecial.addActionListener(e -> cardLayout.show(containerPane, "MAIN_MENU"));
        specialPanel.add(backFromSpecial);

        // 5) Add all cards
        containerPane.add(mainMenuPanel,    "MAIN_MENU");
        containerPane.add(freePlayPanel,    "FREE_PLAY");
        containerPane.add(specialPanel,     "SPECIAL_LEVELS");

        add(containerPane, BorderLayout.CENTER);

        // 6) Small Back-to-Main-Menu at bottom
        JButton topBack = new JButton("← Main Menu");
        topBack.setFont(new Font("Arial", Font.PLAIN, 12));
        topBack.setPreferredSize(new Dimension(120, 30));
        topBack.addActionListener(e -> frame.showScreen("MENU"));
        // Wrap in a panel to add a bit of padding
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        south.setOpaque(false);
        south.add(topBack);
        add(south, BorderLayout.SOUTH);
    }

    private JPanel createFreePlayMenu(GameFrame frame) {
        JPanel p = new JPanel(new GridLayout(4, 1, 10, 10));
        p.setOpaque(false);
        p.setBorder(BorderFactory.createTitledBorder("Free Play"));
        String[] diffs = {"Easy", "Medium", "Hard"};
        for (int lvl = 1; lvl <= 4; lvl++) {
            JPanel lvlPanel = new JPanel(new GridLayout(3, 1, 5, 5));
            lvlPanel.setOpaque(false);
            lvlPanel.setBorder(BorderFactory.createTitledBorder("Level " + lvl));
            for (int d = 0; d < diffs.length; d++) {
                JButton b = new JButton(diffs[d]);
                b.setFont(new Font("Arial", Font.PLAIN, 14));
                final int L = lvl, D = d;
                b.addActionListener(e -> frame.startGame(L, D));
                lvlPanel.add(b);
            }
            p.add(lvlPanel);
        }
        return p;
    }

    private JPanel createSpecialLevelsMenu(GameFrame frame) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setBorder(BorderFactory.createTitledBorder("Special Levels"));
        JButton invis = new JButton("Invisible Snake Mode");
        invis.setFont(new Font("Arial", Font.PLAIN, 14));
        invis.addActionListener(e -> activateInvisibleSnakeMode(frame));
        p.add(invis);
        return p;
    }

    private void activateInvisibleSnakeMode(GameFrame frame) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            boolean inv = false;
            @Override public void run() {
                inv = !inv;
                frame.getGamePanel().setSnakeInvisible(inv);
            }
        }, 0, 15000);
    }
}
