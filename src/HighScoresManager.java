import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighScoresManager {
    private static final int MAX_SCORES = 10;
    private static final String FILENAME = "highScores.json";

    private List<Score> highScores;

    public HighScoresManager() {
        highScores = new ArrayList<>();
        loadScoresFromFile();
    }

    private void loadScoresFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            String json = jsonBuilder.toString();

            Type listType = new TypeToken<List<Score>>() {}.getType();
            Gson gson = new GsonBuilder().create();
            highScores = gson.fromJson(json, listType);
        } catch (IOException e) {
            System.out.println("Error loading high scores: " + e.getMessage());
        }
    }

    private void saveScoresToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(highScores);
            writer.write(json);
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
