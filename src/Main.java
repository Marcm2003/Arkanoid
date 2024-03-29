import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        JFrame frame = new JFrame("Arkanoid 1.2.0");
        ImageIcon icon = new ImageIcon("../resources/img/icon.png");
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(460, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        Game game = new Game();
        frame.add(game);
        frame.setVisible(true);
        while (true) {
            game.update();
            game.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}