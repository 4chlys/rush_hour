package main.java.model.game;

import java.io.Serializable;
import java.util.Date;

public class Highscore implements Serializable {
    private int moves;
    private int timeInSeconds;
    private Date date;

    public Highscore(int moves, int timeInSeconds, Date date) {
        this.moves = moves;
        this.timeInSeconds = timeInSeconds;
        this.date = date;
    }

    public String getFormattedTime() {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public int getMoves() {
        return moves;
    }

    public int getTimeInSeconds() {
        return timeInSeconds;
    }

    public Date getDate() {
        return date;
    }
}
