import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighScoresManager {
    private static final int MAX_SCORES = 10;
    private static final String FILENAME = "highScores.JSON";

    private List<Score> highScores;

    public HighScoresManager() {
        highScores = new ArrayList<>();
        loadScoresFromFile();
    }

    private void loadScoresFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String initials = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());
                    highScores.add(new Score(initials, score));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading high scores: " + e.getMessage());
        }
    }

    private void saveScoresToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Score score : highScores) {
                writer.write(score.initials() + ": " + score.score());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving high scores: " + e.getMessage());
        }
    }

    public List<Score> getHighScores() {
        return highScores;
    }

    public void addScore(String initials, int score) {
        highScores.add(new Score(initials, score));
        sortScores();
        trimScores();
        saveScoresToFile();
    }

    private void sortScores() {
        highScores.sort(Comparator.comparingInt(Score::score).reversed());
    }

    private void trimScores() {
        if (highScores.size() > MAX_SCORES) {
            highScores = highScores.subList(0, MAX_SCORES);
        }
    }

    public record Score(String initials, int score) {
        public String getInitials() {
            return initials;

        }

        public int getScore() {
            return score;
        }
    }
}
