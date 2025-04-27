import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DifficultySelectionPanel extends JPanel {
    private GameFrame gameFrame;
    private ActionListener difficultySelectionListener;  // Declare the listener

    public DifficultySelectionPanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel title = new JLabel("Select Difficulty");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.BLACK);
        gbc.gridy = 0;
        add(title, gbc);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(makeButton("Easy", () -> fireDifficultySelected(Difficulty.EASY)));
        buttonRow.add(makeButton("Medium", () -> fireDifficultySelected(Difficulty.MEDIUM)));
        buttonRow.add(makeButton("Hard", () -> fireDifficultySelected(Difficulty.HARD)));

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

    // Method to set the listener for difficulty selection
    public void setOnDifficultySelectedListener(ActionListener listener) {
        this.difficultySelectionListener = listener;  // Assign the listener
    }

    // Method to trigger the listener when a difficulty is selected
    private void fireDifficultySelected(Difficulty difficulty) {
        if (difficultySelectionListener != null) {
            difficultySelectionListener.actionPerformed(new java.awt.event.ActionEvent(this, java.awt.event.ActionEvent.ACTION_PERFORMED, difficulty.name()));
        }
    }
}
