import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

//Music imports
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;


public class Game extends JPanel {
    int numColumns = 13;  // Num of colums
    int numRows = 1;    // Num of rows
    int blockWidth = 34; // Block width
    int blockHeight = 20; // Block height
    int gap = 0; // Block-block gap
    private static final int maxLevel = 3; // Num of levels
    private final ArrayList<Block> blocks;
    private final Ball ball;
    private final Player player;
    private int level;
    private int numLives;
    private int score;
    private boolean paused;
    private boolean gameOver;
    private boolean devMode;
    private int collitions;
    private int updates;


    public Game() {
        blocks = new ArrayList<>();
        ball = new Ball(230, 400, 15, 0, 4, Color.WHITE);
        player = new Player(200,550, 80, 10, Color.GRAY, 15);
        level = 1;
        numLives = 3;
        score = 0;
        collitions = 0;
        paused = true;
        gameOver = false;
        devMode = false;

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
                        if (paused) {
                            paused = false;
                        }

                    }
                    case KeyEvent.VK_D -> devMode = !devMode;
                }
            }
        });
        setFocusable(true);
        setBackground(Color.BLACK);
        initBlocks();  // Initialize blocks
    }

    private Clip loadSound(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
    }


    private void initBlocks() {
        for (int j = 0; j < numRows ; j++) {

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



    private void reset() {
        blocks.clear();
        ball.reset(230, 400,15, 0,4, Color.white);
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

        for (Block block : blocks) {
            block.draw(g);
        }
        ball.draw(g);
        player.draw(g);
        drawHUD(g);
        if (gameOver) {
            paused = false;
            drawGameOver(g);
        }
        if (paused) {
            drawStop(g);
        }
        if (devMode) {
            drawDev(g);
        }
    }


    private void drawHUD(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Press Start", Font.BOLD, 20));
        g.drawString("Level: " + level, 60, 20);
        g.drawString("Score: " + score, 180, 20);
        g.drawString("Lives: " + numLives, 290, 20);
    }


    private void drawGameOver(Graphics g) {
        g.setColor(Color.magenta);
        g.drawString("GAME OVER", 160, 300);
        g.drawString("Press ENTER to play again", 100, 320);
    }

    private void drawStop(Graphics g) {
        g.setColor(Color.magenta);
        g.drawString("GAME PAUSED", 160, 300);
        g.drawString("Press ENTER to continue playing", 100, 320);
    }

    private void drawDev(Graphics g) {
        g.setColor(Color.magenta);
        g.setFont(new Font("Dialog PLAIN", Font.BOLD, 15));
        g.drawString("DEVELOP MODE", 160, 300);
        g.drawString("p pos: " + player.getX()+" "+ player.getY(), 290, 40);
        g.drawString("p wh: " + player.getWidth(), 290, 60);
        g.drawString("b pos: " + ball.getX() +" "+ ball.getY(), 290,80);
        g.drawString("Blocks: " + getBlocks(), 290,100);
        g.drawString("collitions: " + collitions, 290,120);
        g.drawString("p vel: " + player.getSpeed(), 290, 140);
        int fps = updates;
        g.drawString("FPS: " + fps, 290,160);
        g.drawString("version: 0.3 ", 0,520);
        g.drawString("Marc Martínez Miró", 0,540);
    }

    public boolean isGameOver() {
        paused = false;
        return numLives == 0 || blocks.isEmpty();
    }

    public void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (!paused && !isGameOver()) {
            updates++;
                // Comprobar colisión con los bloques
                for (int i = 0; i < blocks.size(); i++) {
                    Block block = blocks.get(i);
                    if (ball.collidesWith(block.getBounds())) {

                        Clip soundClip = loadSound("resources/sounds/block.wav");
                        soundClip.start();

                        // Verificar si es un bloque azul
                        if (block instanceof BlueBlock blueBlock) {
                            int speedBoost = blueBlock.getSpeedBoost();
                            player.increaseSpeed(speedBoost);
                            // Iniciar un temporizador para revertir el aumento de velocidad
                            Timer timer = new Timer(10000, e -> player.resetSpeed());
                            timer.setRepeats(false);
                            timer.start();
                        }
                        if (block.hitsToBreak == 0){
                            blocks.remove(i);
                            score++;
                        }

                        if(block instanceof RedBlock redBlock){
                        if (block.hitsToFall==0){
                            redBlock.falling = true;

                            Clip soundClip2 = loadSound("resources/sounds/redBlock.wav");
                            soundClip2.start();

                            if (numLives == 0) {
                                blocks.remove(i);
                            }
                        }
                        if (redBlock.collidesWith(player.getBounds())) {
                            gameOver = true;
                        }
                        if (redBlock.getBottom() > getHeight()) {
                            blocks.remove(i);
                        }
                        }

                        block.hitsToBreak --;
                        block.hitsToFall --;
                        Clip soundClip3 = loadSound("resources/sounds/pip.wav");
                        soundClip3.start();
                        ball.reverseY();


                        if (blocks.isEmpty()) {
                            levelUp();
                        }
                        break;
                    }
                    if(block instanceof RedBlock redBlock){
                        if (redBlock.falling){
                            redBlock.move();
                        }
                    }
                }



            // Check collition with player
            if (ball.collidesWith(player.getBounds())) {
                Clip soundClip = loadSound("resources/sounds/player.wav");
                soundClip.start();
                collitions++;
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

                Clip soundClip = loadSound("resources/sounds/wall.wav");
                soundClip.start();

                ball.reverseY();
                ball.setY(0);
            }

            // Check ball top with the sides
            if (ball.getLeft() < 0 || ball.getRight() > getWidth()) {
                Clip soundClip = loadSound("resources/sounds/wall.wav");
                soundClip.start();
                ball.reverseX();
            }

            // Check ball top the end
            if (ball.getBottom() > getHeight()) {
                numLives--;
                Clip soundClip2 = loadSound("resources/sounds/live.wav");
                soundClip2.start();
                //background color change beta
                Timer timer = new Timer(200, e -> setBackground(Color.orange));
                timer.setRepeats(false);
                timer.start();
                Timer timer2 = new Timer(400, e -> setBackground(Color.red));
                timer2.setRepeats(false);
                timer2.start();
                Timer timer3 = new Timer(600, e -> setBackground(Color.yellow));
                timer3.setRepeats(false);
                timer3.start();
                Timer timer4 = new Timer(800, e -> setBackground(Color.black));
                timer4.setRepeats(false);
                timer4.start();
                if (numLives == 0) {
                    gameOver = true;
                    Clip soundClip3 = loadSound("resources/sounds/gameover.wav");
                    soundClip3.start();
                } else {
                    ball.reset(230, 400, 15, 0, 5, Color.WHITE);
                    paused = true;
                }
            }
        } else {
            paused = true;
        }
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }


    public void levelUp() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (level == maxLevel) {
            gameOver = true;
        } else {
            Clip soundClip = loadSound("resources/sounds/levelUP.wav");
            soundClip.start();
            level++;
            blocks.clear();
            ball.reset(230, 400,ball.getDiameter()/2, 0,4, Color.white);
            player.reset(230, 550);
            initBlocks();
            paused = true;
        }
    }

}