import java.awt.Color;
import java.awt.Graphics;

class BlueBlock extends Block {
    private final int speedBoost;

    public BlueBlock(int x, int y, int width, int height, int speedBoost) {
        super(x, y, width, height);
        this.speedBoost = speedBoost;
    }

    @Override
    public void draw(Graphics g) {
        if (visible) {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
            g.setColor(Color.BLUE);
            g.fillRect(x + 1, y + 1, width - 1, height - 1);
        }
    }

    public int getSpeedBoost() {
        return speedBoost;
    }
}