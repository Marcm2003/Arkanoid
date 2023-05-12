import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player {
    private final int originalSpeed;
    private int x;
    private int y;
    private final int width;
    private final int height;
    private int speed;

    private final Color color;


    public Player(int x, int y, int width, int height, Color color, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.speed = speed;
        this.originalSpeed = speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getSpeed() {
        return speed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void moveLeft() {
        if (x  >= 0) {
            x -= speed;
        }
    }

    public void moveRight() {
        if (x + getWidth() < 460) {
            x += speed;
        }
        if (x + getWidth() > 460) {
            x = 460 - speed;
        }
    }

    public void reset(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public void increaseSpeed(int speedBoost) {
        if(speed < 30){
            int boostSpeed = 2;
            speed = boostSpeed * speed;
        }
    }

    public void resetSpeed() {
        speed = originalSpeed;
    }
}
