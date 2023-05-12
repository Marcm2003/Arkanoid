import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


class Music {
    public Music() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    }

    //creem el clip
    private Clip loadSound(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
    }

    Clip soundClip1 = loadSound("resources/sounds/block.wav");

    Clip soundClip2 = loadSound("resources/sounds/redBlock.wav");

    Clip soundClip3 = loadSound("resources/sounds/pip.wav");

    Clip soundClip4 = loadSound("resources/sounds/player.wav");

    Clip soundClip5 = loadSound("resources/sounds/wall.wav");

    Clip soundClip6 = loadSound("resources/sounds/wall.wav");

    Clip soundClip7 = loadSound("resources/sounds/live.wav");

    Clip soundClip8 = loadSound("resources/sounds/gameover.wav");

    Clip soundClip9 = loadSound("resources/sounds/levelUP.wav");
}