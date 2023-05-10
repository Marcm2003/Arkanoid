import java.awt.*;

class RedBlock extends Block {
    public boolean falling = false;

    public RedBlock(int x, int y, int width, int height) {
        super(x, y, width, height);
        hitsToBreak = 3;
        hitsToFall = 1;
    }

    public int getBottom() {
        return y + height;
    }
    public void move() {
        int ySpeed = 1;
        y += ySpeed;
    }

    public boolean collidesWith(Rectangle rect) {
        return getBounds().intersects(rect);
    }

    @Override
    public void draw(Graphics g) {
        if (visible) {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
            g.setColor(Color.red);
            g.fillRect(x + 1, y + 1, width - 1, height - 1);
        }
    }
}