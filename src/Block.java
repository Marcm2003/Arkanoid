import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

abstract class Block {
    protected int x;
    protected int y;
    protected final int width;
    protected final int height;
    protected boolean visible = true;
    protected Color color;
    protected int hitsToBreak;
    protected int hitsToFall;

    public Block(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

    public int getHeight() {
        return height;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public abstract void draw(Graphics g);

    public int getHitsToBreak() {
        return hitsToBreak;
    }

    public void setHitsToBreak(int hitsToBreak) {
        this.hitsToBreak = hitsToBreak;
    }

    public int getHitsToFall() {
        return hitsToFall;
    }

    public void setHitsToFall(int hitsToFall) {
        this.hitsToFall = hitsToFall;
    }
}