package main.java.model.user;

import main.java.model.enums.Difficulty;
import main.java.model.game.Highscore;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static main.java.model.enums.Difficulty.BEGINNER;

//saves all the information about a user
public class User implements Serializable {
    private String username;
    private String password;
    private int currentLevel;
    private Difficulty currentDifficulty;
    private Map<Difficulty, Map<Integer, Highscore>> highscores;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.currentLevel = 1;
        this.currentDifficulty = BEGINNER;
        this.highscores = new HashMap<>();
        for (Difficulty diff : Difficulty.values()) {
            highscores.put(diff, new HashMap<>());
        }
    }

    public void updateHighScore(Difficulty difficulty, int level, int moves, int timeInSeconds) {
        Map<Integer, Highscore> difficultyScores = highscores.get(difficulty);

        if (!difficultyScores.containsKey(level) || isNewScoreBetter(difficultyScores.get(level), moves, timeInSeconds)) {
            difficultyScores.put(level, new Highscore(moves, timeInSeconds, new Date()));
        }
    }

    private boolean isNewScoreBetter(Highscore existingScore, int newMoves, int newTime) {
        if (newMoves < existingScore.getMoves()) {
            return true;
        } else if (newMoves == existingScore.getMoves()) {
            return newTime < existingScore.getTimeInSeconds();
        }
        return false;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public Difficulty getCurrentDifficulty() {
        return currentDifficulty;
    }

    public void setCurrentLvlDiff(int currentLevel, Difficulty currentDifficulty) {
        this.currentLevel = currentLevel;
        this.currentDifficulty = currentDifficulty;
    }

    public Map<Difficulty, Map<Integer, Highscore>> getHighscores() {
        return highscores;
    }
}