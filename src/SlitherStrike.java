import javax.swing.*; // Game window aur UI components ki libraryi

public class SlitherStrike {
    public static void main(String[] args) {
        // Ensure the GUI is started in the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create an instance of GameFrame
                GameFrame frame = new GameFrame();
                
                // Make the game window visible
                frame.setVisible(true);
            }
        });
    }
}
