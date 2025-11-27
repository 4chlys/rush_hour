package main.java.view.highscoreview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.model.enums.Difficulty;
import main.java.model.game.Highscore;
import main.java.model.user.User;

import java.text.SimpleDateFormat;
import java.util.Map;

public class HighscorePresenter {
    private HighscoreView highscoreView;
    private User user;

    public HighscorePresenter(HighscoreView highscoreView, User user) {
        this.highscoreView = highscoreView;
        this.user = user;

        addEventHandlers();

        if (user == null) {
            showGuestUserMessage();
        } else {
            updateView();
        }
    }

    private void showGuestUserMessage() {
        for (Difficulty difficulty : Difficulty.values()) {
            Label guestMessage = new Label("Guest scores are not registered.\nPlease create an account to track your highscores.");
            guestMessage.getStyleClass().add("guest-message");
            guestMessage.setAlignment(Pos.CENTER);

            VBox messageBox = new VBox(guestMessage);
            messageBox.setAlignment(Pos.CENTER);
            messageBox.setPadding(new Insets(20));

            switch (difficulty) {
                case BEGINNER:
                    highscoreView.getBeginnerVBox().getChildren().add(messageBox);
                    break;
                case INTERMEDIATE:
                    highscoreView.getIntermediateVBox().getChildren().add(messageBox);
                    break;
                case ADVANCED:
                    highscoreView.getAdvancedVBox().getChildren().add(messageBox);
                    break;
                case EXPERT:
                    highscoreView.getExpertVBox().getChildren().add(messageBox);
                    break;
            }
        }

        highscoreView.setActiveTab(Difficulty.BEGINNER);
    }

    public void addEventHandlers() {
        highscoreView.getBtnClose().setOnAction(event -> ((Stage) highscoreView.getScene().getWindow()).close());
    }

    public void updateView() {
        highscoreView.getBeginnerVBox().getChildren().clear();
        highscoreView.getIntermediateVBox().getChildren().clear();
        highscoreView.getAdvancedVBox().getChildren().clear();
        highscoreView.getExpertVBox().getChildren().clear();

        updateDifficultyTab(Difficulty.BEGINNER);
        updateDifficultyTab(Difficulty.INTERMEDIATE);
        updateDifficultyTab(Difficulty.ADVANCED);
        updateDifficultyTab(Difficulty.EXPERT);

        highscoreView.setActiveTab(user.getCurrentDifficulty());
    }

    private void updateDifficultyTab(Difficulty difficulty) {
        Map<Integer, Highscore> difficultyScores = user.getHighscores().get(difficulty);

        if (difficultyScores == null || difficultyScores.isEmpty()) {
            Label noScores = highscoreView.createNoRecordsLabel();

            addContentToTab(difficulty, noScores);
            return;
        }

        GridPane scoreGrid = highscoreView.createScoreGrid();

        int rowIndex = 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (int level = 1; level <= 4; level++) {
            Highscore score = difficultyScores.get(level);

            Label levelLabel = new Label(String.valueOf(level));
            levelLabel.getStyleClass().add("score-data");

            if (score != null) {
                Label movesLabel = new Label(String.valueOf(score.getMoves()));
                Label timeLabel = new Label(score.getFormattedTime());
                Label dateLabel = new Label(dateFormat.format(score.getDate()));

                movesLabel.getStyleClass().add("score-data");
                timeLabel.getStyleClass().add("score-data");
                dateLabel.getStyleClass().add("score-data");

                scoreGrid.add(levelLabel, 0, rowIndex);
                scoreGrid.add(movesLabel, 1, rowIndex);
                scoreGrid.add(timeLabel, 2, rowIndex);
                scoreGrid.add(dateLabel, 3, rowIndex);
            } else {
                Label noScoreLabel = new Label("No record");
                noScoreLabel.getStyleClass().add("no-score-data");

                scoreGrid.add(levelLabel, 0, rowIndex);
                scoreGrid.add(noScoreLabel, 1, rowIndex, 3, 1);
            }

            rowIndex++;
        }

        addContentToTab(difficulty, scoreGrid);
    }

    private void addContentToTab(Difficulty difficulty, Node node) {
        switch (difficulty) {
            case BEGINNER -> highscoreView.getBeginnerVBox().getChildren().add(node);
            case INTERMEDIATE -> highscoreView.getIntermediateVBox().getChildren().add(node);
            case ADVANCED -> highscoreView.getAdvancedVBox().getChildren().add(node);
            case EXPERT -> highscoreView.getExpertVBox().getChildren().add(node);
        }
    }
}
