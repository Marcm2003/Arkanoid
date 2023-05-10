import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ball {
    private int x, y;
    private int diameter;
    private int xSpeed, ySpeed;
    private Color color;

    public Ball(int x, int y, int diameter, int xSpeed, int ySpeed, Color color) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, diameter, diameter);
    }

    public void move() {
        x += xSpeed;
        y += ySpeed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, diameter, diameter);
    }

    public boolean collidesWith(Rectangle rect) {
        return getBounds().intersects(rect);
    }


    public int getLeft() {
        return x;
    }

    public int getRight() {
        return x + diameter;
    }

    public int getTop() {
        return y;
    }

    public void reverseY() {
        ySpeed = -ySpeed;
    }

    public void reverseX() {
        xSpeed = -xSpeed;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDiameter() {
        return diameter;
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public int getBottom() {
        return y + diameter;
    }

    public void reset(int x, int y, int diameter, int xSpeed, int ySpeed, Color color) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.color = color;
    }

}