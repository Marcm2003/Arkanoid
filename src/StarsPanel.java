import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StarsPanel  {
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
            star.x = random.nextInt(460);
            star.y = random.nextInt(600);
            star.size = random.nextInt(MAX_SIZE - MIN_SIZE) + MIN_SIZE;
            star.brightness = random.nextInt(MAX_BRIGHTNESS - MIN_BRIGHTNESS) + MIN_BRIGHTNESS;
            stars.add(star);
        }
    }


    protected void paintComponent(Graphics g) {
        for (Star star : stars) {
            g.setColor(new Color(star.brightness, star.brightness, star.brightness));
            g.fillOval(star.x, star.y, star.size, star.size);
        }
    }
}