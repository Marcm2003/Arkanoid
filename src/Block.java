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

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public abstract void draw(Graphics g);

}

