import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

public class Game extends JPanel {
    private static final int BLOCK_WIDTH = 35;
    private static final int BLOCK_HEIGHT = 20;
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 13;
    private static final int MAX_LEVEL = 3;
    private final ArrayList<Block> blocks = new ArrayList<>();
    private final Ball ball = new Ball(230, 400,20, 2,5, Color.white);
    private final Player player = new Player(200, 550, 80, 20, Color.GRAY,40);
    private int level = 1;
    private int numLives = 3;
    private int score = 0;
    private boolean paused = true;
    private boolean gameOver = false;
    private final Random random = new Random();

    public Game() {

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> player.moveLeft();
                    case KeyEvent.VK_RIGHT -> player.moveRight();
                    case KeyEvent.VK_P -> paused = !paused;
                    case KeyEvent.VK_ENTER -> {
                        if (gameOver) {
                            reset();  // Reset game on ENTER key press after game over
                        }
                    }
                }
            }
        });
        setFocusable(true);
        setBackground(Color.BLACK);
        initBlocks();  // Initialize blocks
    }

    private final Color[] colors = new Color[]{Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE};

    private void initBlocks() {
        int x, y;
        for (int i = 0; i < NUM_ROWS; i++) {
            Color color = colors[i];
            for (int j = 0; j < NUM_COLS; j++) {
                x = j * BLOCK_WIDTH;
                y = i * BLOCK_HEIGHT + 40;
                blocks.add(new Block(x, y, BLOCK_WIDTH, BLOCK_HEIGHT, color));
            }
        }
    }


    private void reset() {
        blocks.clear();
        ball.reset(230, 400,10, 1,5, Color.white);
        player.reset(200, 550);
        level = 1;
        numLives = 3;
        score = 0;
        paused = false;
        gameOver = false;
        initBlocks();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(getWidth());
            int y = random.nextInt(getHeight());
            int size = random.nextInt(5) + 1;
            g.setColor(Color.WHITE);
            g.fillRect(x, y, size, size);
        }
        drawBlocks(g);
        ball.draw(g);
        player.draw(g);
        drawHUD(g);
        if (gameOver) {
            drawGameOver(g);
        }
    }

    private void drawBlocks(Graphics g) {
        for (Block block : blocks) {
            block.draw(g);
        }
    }


    private void drawHUD(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Press Start", Font.BOLD, 20));
        g.drawString("Level: " + level, 20, 20);
        g.drawString("Score: " + score, 140, 20);
        g.drawString("Lives: " + numLives, 250, 20);
    }


    private void drawGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.drawString("GAME OVER", 160, 300);
        g.drawString("Press ENTER to play again", 100, 320);
    }

    public boolean isGameOver() {
        return numLives == 0 || blocks.isEmpty();
    }

    public void update() {
        if (!paused && !isGameOver()) {
            ball.move();



            // check collision with blocks
            for (int i = 0; i < blocks.size(); i++) {
                Block block = blocks.get(i);
                if (ball.collidesWith(block)) {
                    blocks.remove(i);
                    i--;
                    score++;

                    if (blocks.isEmpty()) {
                        levelUp();
                    }
                    break;
                }
            }

            // check collision with player
            if (ball.collidesWith(player)) {
                int playerCenter = player.getX() + (player.getWidth() / 2);
                int ballCenter = ball.getX() + ball.getDiameter() / 2;
                int collisionAngle = Math.abs(playerCenter - ballCenter);

                ball.setYSpeed(-ball.getYSpeed());  // Reverse Y direction
// Reverse Y direction<<
                if (playerCenter < ballCenter) {
                    // Ball collided with right half of player
                    ball.setXSpeed(collisionAngle / 4);  // Move right
                } else {
                    // Ball collided with left half of player
                    ball.setXSpeed(-collisionAngle / 4);  // Move left
                }

                ball.setY(player.getTop() - ball.getDiameter());
            }


            // check if ball hits the top
            if (ball.getTop() < 0) {
                ball.reverseY();
                ball.setY(0);
            }

            // check if ball hits the sides
            if (ball.getLeft() < 0 || ball.getRight() > getWidth()) {
                ball.reverseX();
            }

            // check if ball hits the bottom
            if (ball.getBottom() > getHeight()) {
                numLives--;
                if (numLives == 0) {
                    gameOver = true;
                } else {
                    ball.reset(230, 400,10, 10,10, Color.white);
                    paused = true;
                }
            }
        } else {
            gameOver = true;
        }
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }


    public void levelUp() {
        if (level == MAX_LEVEL) {
            gameOver = true;
        } else {
            level++;
            blocks.clear();
            //.reset(getWidth() / 2, getHeight() / 2, BALL_SIZE, BALL_SIZE, DELTA_X + level * 2, DELTA_Y + level * 2,
              //      Color.WHITE);
            player.reset(230, 550);
            initBlocks();
            paused = true;
        }
    }


}