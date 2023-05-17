import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    private final Map<String, Clip> soundClips;

    public AudioManager() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        soundClips = new HashMap<>();
        initializeSoundClips();
    }

    private void initializeSoundClips() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        soundClips.put("sound1", loadSoundClip("resources/sounds/block.wav"));
        soundClips.put("sound2", loadSoundClip("resources/sounds/redBlock.wav"));
        soundClips.put("sound3", loadSoundClip("resources/sounds/pip.wav"));
        soundClips.put("sound4", loadSoundClip("resources/sounds/player.wav"));
        soundClips.put("sound5", loadSoundClip("resources/sounds/wall.wav"));
        soundClips.put("sound6", loadSoundClip("resources/sounds/live.wav"));
        soundClips.put("sound7", loadSoundClip("resources/sounds/game-over.wav"));
        soundClips.put("sound8", loadSoundClip("resources/sounds/levelUP.wav"));
    }

    private Clip loadSoundClip(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
    }

    public void playSound(String soundName) {
        Clip clip = soundClips.get(soundName);
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
}