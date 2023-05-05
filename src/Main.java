import javax.swing.JFrame;

public class Main {


    public static void main(String[] args) {
        JFrame frame = new JFrame("Arkanoid");
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