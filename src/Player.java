import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player {
    private int x;
    private int y;
    private final int width;
    private final int height;
    private final Color color;
    private final int speed;
    private final Rectangle bounds;
    private int direction;

    public Player(int x, int y, int width, int height, Color color, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.speed = speed;
        this.bounds = new Rectangle(x, y, width, height);
        this.direction = 0;
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

    public void setDirection(int direction) {
        this.direction = direction;
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

    public Rectangle getBounds() {
        return bounds;
    }

    public void moveLeft() {
        if (x > 0) {
            x -= 10;
        }
    }

    public void moveRight() {
        if (x > 0) {
            x += 10;
        }
    }

    public void stop() {
        setDirection(0);
    }


    public void update() {
        x += direction * speed;
        if (x < 0) {
            x = 0;
        } else if (x + width > Game.WIDTH) {
            x = Game.WIDTH - width;
        }
        bounds.setLocation(x, y);
    }

    public void reset(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getTop() {
        return y;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
}