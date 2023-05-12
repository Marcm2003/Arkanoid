import java.awt.Color;
import java.awt.Graphics;

class GreenBlock extends Block {
    public GreenBlock(int x, int y, int width, int height) {
        super(x, y, width, height);
        hitsToBreak = 3;

    }

    public int getHitsToBreak() {
    	return hitsToBreak;
    }

    public Color getColor() {
    	return color;
    }

    //set the color of the block
    public void setColor(Color color) {
    	this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        if (visible) {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
            if (hitsToBreak == 3) {
                g.setColor(Color.green);
            } else if (hitsToBreak == 2) {
                g.setColor(Color.orange);
            } else if (hitsToBreak == 1) {
                g.setColor(Color.pink);
            }
            g.fillRect(x + 1, y + 1, width - 1, height - 1);
        }
    }
}