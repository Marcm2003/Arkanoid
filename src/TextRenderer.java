import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class TextRenderer {
    public static int collations;
    public static int updates;
    private static Font arcadeFont = loadFont();
    private static final HighScoresManager highScoresManager = new HighScoresManager();


    public static void drawDev(Graphics g, Ball ball, Player player) {
        g.setColor(Color.magenta);
        g.setFont(new Font("Dialog PLAIN", Font.BOLD, 15));
        g.drawString("DEVELOP MODE", 160, 300);
        g.drawString("p pos: " + player.getX() + " " + player.getY(), 290, 40);
        g.drawString("p wh: " + player.getWidth(), 290, 60);
        g.drawString("b pos: " + ball.getX() + " " + ball.getY(), 290, 80);
        g.drawString("collitions: " + collations, 290, 120);
        g.drawString("p vel: " + player.getSpeed(), 290, 140);
        int fps = updates;
        g.drawString("FPS: " + fps, 290, 160);
        g.drawString("version: 1.2.0 ", 0, 520);
        g.drawString("Marc Martínez Miró", 0, 540);
    }

    public static Font loadFont() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            arcadeFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/PressStart2P.TTF"));
            ge.registerFont(arcadeFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return arcadeFont;
    }

    public static void drawHUD(Graphics g, int level, int score, int numLives) {
        g.setColor(Color.RED);
        g.setFont(arcadeFont.deriveFont(Font.BOLD, 15));
        g.drawString("Level: ", 10, 30);
        g.setColor(Color.white);
        g.drawString(String.valueOf(level), 110, 30);
        g.setColor(Color.RED);
        g.drawString("Score: ", 160, 30);
        g.setColor(Color.white);
        g.drawString(String.valueOf(score), 260, 30);
        g.setColor(Color.RED);
        g.drawString("Lives: ", 310, 30);
        g.setColor(Color.white);
        g.drawString(String.valueOf(numLives), 410, 30);
    }

    public static void drawGameOver(Graphics g) {
        int windowWidth = g.getClipBounds().width;
        int windowHeight = g.getClipBounds().height;

        g.setColor(Color.magenta);
        g.setFont(arcadeFont.deriveFont(Font.BOLD, 30));

        String gameOverText = "GAME OVER";
        int gameOverTextWidth = g.getFontMetrics().stringWidth(gameOverText);
        int gameOverTextX = (windowWidth - gameOverTextWidth) / 2;
        int gameOverTextY = windowHeight / 2 - 40;

        g.drawString(gameOverText, gameOverTextX, gameOverTextY);

        g.setFont(arcadeFont.deriveFont(Font.BOLD, 10));

        String initialsText = "Enter your initials to save your score:";
        int initialsTextWidth = g.getFontMetrics().stringWidth(initialsText);
        int initialsTextX = (windowWidth - initialsTextWidth) / 2;
        int initialsTextY = windowHeight / 2;
        g.drawString(initialsText, initialsTextX, initialsTextY);

        String playAgainText = "Press ENTER to play again";
        int playAgainTextWidth = g.getFontMetrics().stringWidth(playAgainText);
        int playAgainTextX = (windowWidth - playAgainTextWidth) / 2;
        int playAgainTextY = windowHeight / 2 + 20;
        g.drawString(playAgainText, playAgainTextX, playAgainTextY);

        String initialsText2 = "INITIALS:";
        int initialsText2Width = g.getFontMetrics().stringWidth(initialsText2);
        int initialsText2X = (windowWidth - initialsText2Width) / 2;
        int initialsText2Y = windowHeight / 2 + 40;
        g.drawString(initialsText2, initialsText2X, initialsText2Y);

        String initialsInputText = Game.initialsInput;
        int initialsInputTextWidth = g.getFontMetrics().stringWidth(initialsInputText);
        int initialsInputTextX = (windowWidth - initialsInputTextWidth) / 2;
        int initialsInputTextY = windowHeight / 2 + 60;
        g.drawString(initialsInputText, initialsInputTextX, initialsInputTextY);

        g.setColor(Color.GREEN);

        String scoresText = "SCORES:";
        int scoresTextWidth = g.getFontMetrics().stringWidth(scoresText);
        int scoresTextX = (windowWidth - scoresTextWidth) / 2;
        int scoresTextY = windowHeight / 2 + 90;
        g.drawString(scoresText, scoresTextX, scoresTextY);

        for (int i = 0; i < highScoresManager.getHighScores().size(); i++) {
            String scoreText = highScoresManager.getHighScores().get(i).getInitials() + ":" + highScoresManager.getHighScores().get(i).getScore();
            int scoreTextWidth = g.getFontMetrics().stringWidth(scoreText);
            int scoreTextX = (windowWidth - scoreTextWidth) / 2;
            int scoreTextY = windowHeight / 2 + 110 + (i * 20);

            g.drawString(scoreText, scoreTextX, scoreTextY);
        }
    }

    public static void drawStart(Graphics g) {
        int windowWidth = g.getClipBounds().width;
        int windowHeight = g.getClipBounds().height;

        g.setColor(Color.magenta);
        g.setFont(arcadeFont.deriveFont(Font.BOLD, 40));

        String arkanoidText = "ARKANOID";
        int arkanoidTextWidth = g.getFontMetrics().stringWidth(arkanoidText);
        int arkanoidTextX = (windowWidth - arkanoidTextWidth) / 2;
        int arkanoidTextY = windowHeight / 2 - 40;

        Random random = new Random(); // For generating random colors

        for (int i = 0; i < arkanoidText.length(); i++) {
            // Generate a random color
            int red = random.nextInt(256);
            int green = random.nextInt(256);
            int blue = random.nextInt(256);
            Color color = new Color(red, green, blue);

            // Config the color before drawing
            g.setColor(color);

            // Draw each letter
            char letter = arkanoidText.charAt(i);
            int letterX = arkanoidTextX + g.getFontMetrics().charWidth(letter) * i;
            g.drawString(String.valueOf(letter), letterX, arkanoidTextY);
        }

        g.setColor(Color.magenta);
        g.setFont(arcadeFont.deriveFont(Font.BOLD, 10));
        String startText = "PRESS ENTER TO START";
        int startTextWidth = g.getFontMetrics().stringWidth(startText);
        int startTextX = (windowWidth - startTextWidth) / 2;
        int startTextY = windowHeight / 2;
        g.drawString(startText, startTextX, startTextY);

        g.setFont(arcadeFont.deriveFont(Font.BOLD, 10));
        g.setColor(Color.green);

        String scoresText = "SCORES:";
        int scoresTextWidth = g.getFontMetrics().stringWidth(scoresText);
        int scoresTextX = (windowWidth - scoresTextWidth) / 2;
        int scoresTextY = windowHeight / 2 + 20;
        g.drawString(scoresText, scoresTextX, scoresTextY);

        for (int i = 0; i < highScoresManager.getHighScores().size(); i++) {
            String scoreText = highScoresManager.getHighScores().get(i).getInitials() + ":" + highScoresManager.getHighScores().get(i).getScore();
            int scoreTextWidth = g.getFontMetrics().stringWidth(scoreText);
            int scoreTextX = (windowWidth - scoreTextWidth) / 2;
            int scoreTextY = windowHeight / 2 + 40 + (i * 20);

            g.drawString(scoreText, scoreTextX, scoreTextY);
        }
    }

    public static void drawStop(Graphics g) {
        g.setFont(arcadeFont.deriveFont(Font.BOLD, 30));
        g.setColor(Color.magenta);

        String stopText = "GAME PAUSED";
        int stopTextWidth = g.getFontMetrics().stringWidth(stopText);
        int stopTextX = (g.getClipBounds().width - stopTextWidth) / 2;
        int stopTextY = g.getClipBounds().height / 2 - 40;
        g.drawString(stopText, stopTextX, stopTextY);

        g.setFont(arcadeFont.deriveFont(Font.BOLD, 15));
        String stopText2 = "PRESS ENTER TO CONTINUE";
        int stopText2Width = g.getFontMetrics().stringWidth(stopText2);
        int stopText2X = (g.getClipBounds().width - stopText2Width) / 2;
        int stopText2Y = g.getClipBounds().height / 2;
        g.drawString(stopText2, stopText2X, stopText2Y);
    }


}
