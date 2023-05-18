import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;


public class Game extends JPanel {
    int numColumns = 13;  // Num of columns
    int numRows = 1;    // Num of rows
    int blockWidth = 34; // Block width
    int blockHeight = 20; // Block height
    int gap = 0; // Block-block gap
    private static final int maxLevel = 3; // Num of levels
    private final ArrayList<Block> blocks;
    private final Ball ball;
    private final Player player;
    private final StarsPanel starsPanel;
    private final AudioManager audioManager;
    private final HighScoresManager highScoresManager;
    private int level;
    private int numLives;
    private int score;
    private boolean start;
    private boolean paused;
    private boolean gameOver;
    private boolean devMode;
    public static String initialsInput = "";



    public Game() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        blocks = new ArrayList<>();
        starsPanel = new StarsPanel();
        audioManager = new AudioManager();
        highScoresManager = new HighScoresManager();
        ball = new Ball(210, 400, 15, 0, 4, Color.WHITE);
        player = new Player(180, 550, 80, 10, Color.GRAY, 15);
        level = 1;
        numLives = 3;
        score = 0;
        start = true;
        paused = false;
        gameOver = false;
        devMode = false;
        audioManager.playSound("sound9");



        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> player.moveLeft();
                    case KeyEvent.VK_RIGHT -> player.moveRight();
                    case KeyEvent.VK_P -> paused = !paused;
                    case KeyEvent.VK_ENTER -> {
                        if (gameOver) {
                            highScoresManager.addScore(initialsInput, score);
                            reset();
                        } else if (start) {
                            start = false;

                        } else if (paused) {
                            paused = false;
                        }
                    }
                    case KeyEvent.VK_F1 -> devMode = !devMode;
                }
                if (gameOver) {

                    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                        if (initialsInput.length() > 0) {
                            initialsInput = initialsInput.substring(0, initialsInput.length() - 1);
                        }
                    } else if (initialsInput.length() < 3) {
                        initialsInput += e.getKeyChar();
                    }
                }
            }

        });
        setFocusable(true);
        setBackground(Color.BLACK);
        initBlocks();  // Initialize blocks
    }



    private void initBlocks() {
        for (int j = 0; j < numRows; j++) {

            int x = 0;
            int y = 50;

            for (int i = 0; i < numColumns; i++) {

                // Generates green block
                GreenBlock greenBlock = new GreenBlock(x, y, blockWidth, blockHeight);
                blocks.add(greenBlock);
                y += blockHeight + gap;

                // Generates red blovk
                RedBlock redBlock = new RedBlock(x, y, blockWidth, blockHeight);
                blocks.add(redBlock);
                y += blockHeight + gap;

                // Generates blue block
                BlueBlock blueBlock = new BlueBlock(x, y, blockWidth, blockHeight, 2);
                blocks.add(blueBlock);
                //y += blockHeight + gap;


                // Updates the positions to go to the next colum
                x += blockWidth + gap;
                y = 50;
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Block block : blocks) {
            block.draw(g);
        }
        starsPanel.paintComponent(g);
        ball.draw(g);
        player.draw(g);
        TextRenderer.drawHUD(g, level, score, numLives);
        if (gameOver) {
            paused = false;
            TextRenderer.drawGameOver(g);
        }
        if (start) {
            TextRenderer.drawStart(g);
        }
        if (paused) {
            TextRenderer.drawStop(g);
        }
        if (devMode) {
            TextRenderer.drawDev(g, ball, player);
        }
    }
    public boolean isGameOver() {
        paused = false;
        return numLives == 0 || blocks.isEmpty();
    }

    public void levelUp() {
        if (level == maxLevel) {
            gameOver = true;
        } else {
            audioManager.playSound("sound8");
            level++;
            blocks.clear();
            ball.reset(230, 400, ball.getDiameter() / 2, 0, 4, Color.white);
            player.reset(230, 550);
            initBlocks();
            start = true;
        }
    }

    private void reset() {
        blocks.clear();
        ball.reset(230, 400, 15, 0, 4, Color.white);
        player.reset(200, 550);
        level = 1;
        numLives = 3;
        score = 0;
        paused = false;
        gameOver = false;
        initBlocks();
    }

    public void update() {
        if (!start && !paused && !isGameOver()) {
            audioManager.stopSound("sound9");
            TextRenderer.updates++;
            // Check collisions with blocks
            for (int i = 0; i < blocks.size(); i++) {
                Block block = blocks.get(i);
                if (ball.collidesWith(block.getBounds())) {


                    audioManager.playSound("sound1");

                    // VCheck if the block is blue
                    if (block instanceof BlueBlock blueBlock) {
                        int speedBoost = blueBlock.getSpeedBoost();
                        player.increaseSpeed(speedBoost);
                        // Init a timer to reset the speed
                        Timer timer = new Timer(10000, e -> player.resetSpeed());
                        timer.setRepeats(false);
                        timer.start();
                    }
                    if (block.hitsToBreak == 0) {
                        blocks.remove(i);
                        score++;
                    }

                    if (block instanceof RedBlock redBlock) {
                        if (block.hitsToFall == 0) {
                            redBlock.falling = true;


                            audioManager.playSound("sound2");

                            if (numLives == 0) {
                                blocks.remove(i);
                            }
                        }
                        if (redBlock.collidesWith(player.getBounds())) {
                            numLives--;
                        }

                    }


                    audioManager.playSound("sound1");
                    ball.reverseY();


                    if (blocks.isEmpty()) {
                        levelUp();
                    }

                    block.hitsToBreak--;
                    block.hitsToFall--;

                    break;
                }
                if (block instanceof RedBlock redBlock) {
                    if (redBlock.falling) {
                        redBlock.move();
                    }
                    if (redBlock.getBottom() > getHeight()) {
                        blocks.remove(i);
                    }
                }
            }


            // Check collition with player
            if (ball.collidesWith(player.getBounds())) {

                audioManager.playSound("sound3");
                TextRenderer.collations++;
                int playerCenter = player.getX() + (player.getWidth() / 2);
                int ballCenter = ball.getX() + (ball.getDiameter() / 2);
                int collisionAngle = Math.abs(playerCenter - ballCenter);

                ball.reverseY();  // Invert direction Y

                if (playerCenter < ballCenter) {
                    // Ball impact on the right side of player
                    ball.setXSpeed(collisionAngle / 4);  // Move to the right
                } else {
                    // Ball impact on the left side of player
                    ball.setXSpeed(-collisionAngle / 4);  // Move to the left
                }

                // Update position
                ball.setY(player.getY() - ball.getDiameter() - 1);
            }


            // Move the ball
            ball.move();

            // Check ball top collition
            if (ball.getTop() < 0) {


                audioManager.playSound("sound4");

                ball.reverseY();
                ball.setY(0);
            }

            // Check ball top with the sides
            if (ball.getLeft() < 0 || ball.getRight() > getWidth()) {

                audioManager.playSound("sound5");
                ball.reverseX();
            }

            // Check ball top the end
            if (ball.getBottom() > getHeight()) {
                numLives--;

                audioManager.playSound("sound6");
                //background color change beta
                Timer timer = new Timer(100, e -> setBackground(Color.orange));
                timer.setRepeats(false);
                timer.start();
                Timer timer2 = new Timer(150, e -> setBackground(Color.red));
                timer2.setRepeats(false);
                timer2.start();
                Timer timer3 = new Timer(200, e -> setBackground(Color.white));
                timer3.setRepeats(false);
                timer3.start();
                Timer timer4 = new Timer(250, e -> setBackground(Color.yellow));
                timer4.setRepeats(false);
                timer4.start();
                Timer timer5 = new Timer(300, e -> setBackground(Color.black));
                timer5.setRepeats(false);
                timer5.start();
                if (numLives == 0) {
                    gameOver = true;
                    audioManager.playSound("sound7");
                } else {
                    player.reset(200, 550);
                    ball.reset(230, 400, 15, 0, 5, Color.WHITE);
                    paused = true;
                }
            }
        }
    }
}
