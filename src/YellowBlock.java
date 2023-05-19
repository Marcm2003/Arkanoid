import java.awt.Color;
import java.awt.Graphics;

class YellowBlock extends Block {
    public YellowBlock(int x, int y, int width, int height) {
        super(x, y, width, height);
        hitsToBreak = 0;
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
            g.setColor(Color.yellow);
            g.fillRect(x + 1, y + 1, width - 1, height - 1);
        }
    }
}