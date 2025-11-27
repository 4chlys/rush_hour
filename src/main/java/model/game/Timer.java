package main.java.model.game;

import main.java.model.enums.Difficulty;

public class Timer {
    private int elapsedSeconds;
    private boolean isPaused;
    private Difficulty difficulty;

    public Timer(Difficulty difficulty) {
        this.elapsedSeconds = 0;
        this.isPaused = false;
        this.difficulty = difficulty;
    }

    public boolean isTimeUp() {
        return !isPaused && getRemainingTimeInSeconds() <= 0;
    }

    public void incrementTime() {
        if (!isPaused) {
            elapsedSeconds++;
        }
    }

    public int getMaxTimeInSeconds() {
        return switch (difficulty) {
            case BEGINNER -> 3 * 60;
            case INTERMEDIATE, ADVANCED -> 5 * 60;
            case EXPERT -> 7 * 60;
        };
    }

    public int getRemainingTimeInSeconds() {
        int maxTime = getMaxTimeInSeconds();
        int remaining = maxTime - elapsedSeconds;
        return Math.max(0, remaining);
    }

    public String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }

    public int getElapsedSeconds() {
        return elapsedSeconds;
    }

    public void setElapsedSeconds(int elapsedSeconds) {
        this.elapsedSeconds = elapsedSeconds;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isPaused() {
        return isPaused;
    }
}
