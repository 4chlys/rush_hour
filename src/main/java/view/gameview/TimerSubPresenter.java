package main.java.view.gameview;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.util.Duration;
import main.java.model.game.Game;
import main.java.view.homemenuview.HomeMenuPresenter;
import main.java.view.homemenuview.HomeMenuView;

public class TimerSubPresenter {
    private GameView gameView;
    private Game game;
    private Timeline timeline;
    private boolean isTimerRunning;
    private GamePresenter gamePresenter;

    public TimerSubPresenter(Game game, GameView gameView, GamePresenter gamePresenter) {
        this.game = game;
        this.gameView = gameView;
        this.isTimerRunning = false;
        this.gamePresenter = gamePresenter;
    }

    void setupTimer() {
        game.getTimer().setElapsedSeconds(0);

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            game.getTimer().incrementTime();
            updateTimerDisplay();

            if (game.getTimer().isTimeUp() && !game.getTimer().isPaused()) {
                timeIsUp();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);

        updateTimerDisplay();
    }

    void updateTimerDisplay() {
        int remainingTime = game.getTimer().getRemainingTimeInSeconds();
        String formattedTime = game.getTimer().formatTime(remainingTime);
        gameView.getPuzzleSubView().getLblTimer().setText("Time: " + formattedTime);

        if (remainingTime < 30) {
            gameView.getPuzzleSubView().getLblTimer().setStyle("-fx-text-fill: red;");
        } else {
            gameView.getPuzzleSubView().getLblTimer().setStyle("-fx-text-fill: #def4ff;");
        }
    }

    void startTimer() {
        timeline.play();
        isTimerRunning = true;
        game.getTimer().setPaused(false);
    }

    void stopTimer() {
        if (timeline != null) {
            timeline.stop();
            isTimerRunning = false;
        }
    }

    void pauseTimer() {
        timeline.pause();
        isTimerRunning = false;
        game.getTimer().setPaused(true);
    }

    void resumeTimer() {
        timeline.play();
        isTimerRunning = true;
        game.getTimer().setPaused(false);
    }

    void timeIsUp() {
        stopTimer();

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Time's up! You couldn't solve the puzzle in time.\nWould you like to restart?",
                    ButtonType.YES, ButtonType.NO);
            alert.setTitle("Time's Up");
            alert.setHeaderText("Time Expired");
            alert.setHeight(275);

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("/stylesheet/alertview.css");
            dialogPane.getStyleClass().add("time-up");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    game.restartPuzzle();
                    if (gamePresenter != null) {
                        startTimer();
                        gamePresenter.initialView();
                        gamePresenter.updateView();
                        gamePresenter.getVehicleSubPresenter().addVehicleEventHandlers();
                    }
                } else {
                    HomeMenuView homeMenuView = new HomeMenuView();
                    HomeMenuPresenter homeMenuPresenter = new HomeMenuPresenter(homeMenuView, game.getCurrentUser());
                    gameView.getScene().setRoot(homeMenuView);
                    homeMenuView.getScene().getStylesheets().clear();
                    homeMenuView.getScene().getStylesheets().add("/stylesheet/homemenuview.css");
                    homeMenuView.getScene().getWindow().sizeToScene();
                    homeMenuView.getScene().getWindow().centerOnScreen();
                }
            });
        });
    }

    boolean isTimerRunning() {
        return isTimerRunning;
    }
}