package main.java.view.creditsview;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class CreditsPresenter {
    private CreditsView view;

    public CreditsPresenter(CreditsView view) {
        this.view = view;
        updateView();
    }

    private void updateView() {
        String creditsText = loadCreditsText();
        view.setCreditsText(creditsText);
        animateCredits();
    }

    private String loadCreditsText() {
        InputStream inputStream = getClass().getResourceAsStream("/txtfiles/credits.txt");

        if (inputStream == null) {
            return "Credits file not found.";
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            return "Error reading credits.";
        }
    }

    private void animateCredits() {
        Text text = view.getCreditsText();
        text.setTranslateY(600);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(40), event -> text.setTranslateY(text.getTranslateY() - 1)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
