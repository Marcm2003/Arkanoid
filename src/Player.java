import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player {
    private int originalSpeed = 0;
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;

    private int boostSpeed = 2;
    private final Color color;




    public Player(int x, int y, int width, int height, Color color, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.speed = speed;
        this.originalSpeed=speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLeft() {
        return getX();
    }

    public int getRight() {
        return getX() + getWidth();
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }

    public int getSpeed() {
        return speed;
    }

    public void moveLeft() {
        if (x - speed >= 0) {
            x -= speed;
        }
    }

    public void moveRight() {
        if (x + speed < 460) {
            x += speed;
        }
        if (x + speed > 460) {
            x = 460 - speed;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
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
        speed = boostSpeed * speed;
        }
    }

    public void resetSpeed() {
        speed = originalSpeed;
    }
}