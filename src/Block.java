import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

class Block {
    private int x;
    private int y;
    private final int width;
    private final int height;
    private boolean visible = true;
    private final Color color;

    public Block(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
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

    public void draw(Graphics g) {
        if (visible) {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
            g.setColor(color);
            g.fillRect(x + 1, y + 1, width - 1, height - 1);
        }
    }


}
