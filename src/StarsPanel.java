import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

public class StarsPanel extends JPanel {
    private static final int NUM_STARS = 50;
    private static final int MAX_SIZE = 5;
    private static final int MIN_SIZE = 1;
    private static final int MAX_BRIGHTNESS = 255;
    private static final int MIN_BRIGHTNESS = 128;

    private final Random random = new Random();
    private final List<Star> stars = new ArrayList<>();

    private static class Star {
        int x, y;
        int size;
        int brightness;
    }

    public StarsPanel() {
        for (int i = 0; i < NUM_STARS; i++) {
            Star star = new Star();
            star.x = random.nextInt(getWidth());
            star.y = random.nextInt(getHeight());
            star.size = random.nextInt(MAX_SIZE - MIN_SIZE) + MIN_SIZE;
            star.brightness = random.nextInt(MAX_BRIGHTNESS - MIN_BRIGHTNESS) + MIN_BRIGHTNESS;
            stars.add(star);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Star star : stars) {
            star.size = random.nextInt(MAX_SIZE - MIN_SIZE) + MIN_SIZE;
            int brightnessDelta = random.nextInt(MAX_BRIGHTNESS - MIN_BRIGHTNESS);
            if (star.brightness + brightnessDelta > MAX_BRIGHTNESS) {
                star.brightness = MAX_BRIGHTNESS;
            } else {
                star.brightness += brightnessDelta;
            }

            Color starColor = new Color(star.brightness, star.brightness, star.brightness);
            g.setColor(starColor);
            g.fillRect(star.x, star.y, star.size, star.size);
        }

        // draw other components
    }
}