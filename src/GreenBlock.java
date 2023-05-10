import java.awt.Color;
import java.awt.Graphics;

class GreenBlock extends Block {
    public GreenBlock(int x, int y, int width, int height) {
        super(x, y, width, height);
        hitsToBreak = 1;

    }

    @Override
    public void draw(Graphics g) {
        if (visible) {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
            g.setColor(Color.green);
            g.fillRect(x + 1, y + 1, width - 1, height - 1);
        }
    }
}