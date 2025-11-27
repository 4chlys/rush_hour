package main.java.view.gameview;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.model.enums.Difficulty;
import main.java.model.exceptions.PuzzleLoadException;
import main.java.model.game.Game;
import main.java.model.user.User;
import main.java.model.user.UserManager;
import main.java.view.creditsview.CreditsPresenter;
import main.java.view.creditsview.CreditsView;
import main.java.view.homemenuview.HomeMenuPresenter;
import main.java.view.homemenuview.HomeMenuView;
import main.java.view.loginview.LoginPresenter;
import main.java.view.loginview.LoginView;
import main.java.view.selectlevelview.SelectLevelPresenter;
import main.java.view.selectlevelview.SelectLevelView;

public class GamePresenter {
    private GameView gameView;
    private Game game;
    private User user;
    private UserManager userManager;
    private VehicleSubPresenter vehicleSubPresenter;
    private TimerSubPresenter timerSubPresenter;

    public GamePresenter(GameView gameView, Game game, User user) {
        this.gameView = gameView;
        this.game = game;
        this.user = user;
        this.userManager = new UserManager();

        try {
            // Initialize sub-presenters
            this.vehicleSubPresenter = new VehicleSubPresenter(game, gameView, this);
            this.timerSubPresenter = new TimerSubPresenter(game, gameView, this);

            timerSubPresenter.setupTimer();
            timerSubPresenter.startTimer();

            initialView();
            updateView();
            addEventHandlers();
        } catch (PuzzleLoadException e) {
            handlePuzzleLoadException(e);
        } catch (Exception e) {
            handleGenericException(e);
        }
    }

    private void addEventHandlers() {
        // add draggable vehicles
        vehicleSubPresenter.addVehicleEventHandlers();

        // Add event handlers for buttons and menu items
        gameView.getPuzzleSubView().getBtnGoBack().setOnAction(actionEvent -> returnToHomeMenu());

        gameView.getPuzzleSubView().getBtnRestart().setOnAction(actionEvent -> {
            timerSubPresenter.stopTimer();
            game.restartPuzzle();
            timerSubPresenter.startTimer();
            initialView();
            updateView();
            addEventHandlers();

        });

        gameView.getPuzzleSubView().getBtnPause().setOnAction(actionEvent -> {
            if (timerSubPresenter.isTimerRunning()) {
                timerSubPresenter.pauseTimer();
                updateView();
            } else {
                timerSubPresenter.resumeTimer();
                updateView();
                vehicleSubPresenter.addVehicleEventHandlers();
            }

        });

        gameView.getShowUsernameMenuItem().setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Current User");
            alert.setHeaderText("You are logged in as:");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("/stylesheet/alertview.css");
            if (user != null) {
                alert.setContentText(user.getUsername());
            } else {
                alert.setContentText("Guest");
            }
            alert.showAndWait();
        });

        gameView.getLogoutMenuItem().setOnAction(actionEvent -> returnToHomeMenu());

        gameView.getReturnToLoginMenuItem().setOnAction(actionEvent -> {
            timerSubPresenter.stopTimer();
            LoginView loginView = new LoginView();
            LoginPresenter loginPresenter = new LoginPresenter(loginView);
            gameView.getScene().setRoot(loginView);
            loginView.getScene().getStylesheets().clear();
            loginView.getScene().getStylesheets().add("/stylesheet/loginview.css");
            loginView.getScene().getWindow().sizeToScene();
            loginView.getScene().getWindow().centerOnScreen();
        });

        gameView.getCreditsMenuItem().setOnAction(actionEvent -> {
            try {
                boolean wasRunning = timerSubPresenter.isTimerRunning();
                if (wasRunning) {
                    timerSubPresenter.pauseTimer();
                }

                CreditsView creditsView = new CreditsView();
                CreditsPresenter creditsPresenter = new CreditsPresenter(creditsView);

                Stage creditsStage = new Stage();
                creditsStage.setTitle("About Us");
                creditsStage.setScene(new Scene(creditsView));
                creditsStage.getScene().getStylesheets().add("/stylesheet/textviews.css");
                creditsStage.initModality(Modality.APPLICATION_MODAL);  // Make it modal
                creditsStage.showAndWait();

                if (wasRunning) {
                    timerSubPresenter.resumeTimer();
                }
            } catch (Exception e) {
                handleGenericException(e);
            }
        });

        gameView.getBeginnerLevelMenuItem().setOnAction(actionEvent -> {
            openSelectLevelView(Difficulty.BEGINNER);
            updateView();
            vehicleSubPresenter.addVehicleEventHandlers();
        });

        gameView.getIntermediateLevelMenuItem().setOnAction(actionEvent -> {
            openSelectLevelView(Difficulty.INTERMEDIATE);
            updateView();
            vehicleSubPresenter.addVehicleEventHandlers();
        });

        gameView.getAdvancedLevelMenuItem().setOnAction(actionEvent -> {
            openSelectLevelView(Difficulty.ADVANCED);
            updateView();
            vehicleSubPresenter.addVehicleEventHandlers();
        });

        gameView.getExpertLevelMenuItem().setOnAction(actionEvent -> {
            openSelectLevelView(Difficulty.EXPERT);
            updateView();
            vehicleSubPresenter.addVehicleEventHandlers();
        });
    }

    void updateView() {
        gameView.getPuzzleSubView().getLblLevel().setText("Level " + game.getLevel());
        gameView.getPuzzleSubView().getLblMoves().setText("Moves: " + game.getMoves());
        gameView.getPuzzleSubView().getLblDifficulty().setText("Difficulty: " + game.getDifficulty());
        timerSubPresenter.updateTimerDisplay();
        gameView.getPuzzleSubView().setBtnPause(timerSubPresenter.isTimerRunning() ? "Pause" : "Resume");
        gameView.getBoardGrid().getChildren().clear();
        vehicleSubPresenter.createVehicleNodes();
    }

    void initialView() {
        gameView.getPuzzleGrid().getChildren().clear();
        vehicleSubPresenter.createVehicleNodes();
        vehicleSubPresenter.createPuzzleGridVehicles();
    }

    private void openSelectLevelView(Difficulty difficulty) {
        timerSubPresenter.stopTimer();

        try {
            SelectLevelView selectLevelView = new SelectLevelView();
            SelectLevelPresenter selectLevelPresenter =
                    new SelectLevelPresenter(user, difficulty, selectLevelView, gameView.getScene());

            Stage levelSelectStage = new Stage();
            levelSelectStage.setTitle(difficulty.toString());
            levelSelectStage.setScene(new Scene(selectLevelView));
            levelSelectStage.getScene().getStylesheets().add("/stylesheet/selectlevelview.css");
            levelSelectStage.initModality(Modality.APPLICATION_MODAL);

            levelSelectStage.showAndWait();

        } catch (Exception e) {
            handleGenericException(e);
        }
    }

    private void returnToHomeMenu() {
        timerSubPresenter.stopTimer();
        HomeMenuView homeMenuView = new HomeMenuView();
        HomeMenuPresenter homeMenuPresenter = new HomeMenuPresenter(homeMenuView, null);
        gameView.getScene().setRoot(homeMenuView);
        homeMenuView.getScene().getStylesheets().clear();
        homeMenuView.getScene().getStylesheets().add("/stylesheet/homemenuview.css");
        homeMenuView.getScene().getWindow().sizeToScene();
        homeMenuView.getScene().getWindow().centerOnScreen();
    }

    void displayVictoryMessage(int moveCount) {
        int elapsedTime = game.getTimer().getElapsedSeconds();
        String formattedTime = game.getTimer().formatTime(elapsedTime);

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Level Completed in " + formattedTime + " with " + moveCount + " moves!\n" +
                            "Press OK to start next Puzzle",
                    ButtonType.OK);
            alert.setTitle("Level Completed");
            alert.setHeaderText("Congratulations");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("/stylesheet/alertview.css");
            dialogPane.getStyleClass().add("level-completed");

            alert.showAndWait();
        });
    }

    void loadNextPuzzleAndUpdateView() {
        game.loadNextPuzzle();
        if (game.getLevel() == 5 && game.getDifficulty() == Difficulty.ADVANCED) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "All levels completed, thank you for playing!", ButtonType.OK);
            alert.setTitle("Game Completed");
            alert.setHeaderText("Congratulations");
            alert.showAndWait();
        }
        timerSubPresenter.startTimer();
        initialView();
        updateView();
        addEventHandlers();

        if (this.user != null) {
            userManager.updateUser(this.user);
        }
    }

    void handlePuzzleLoadException(PuzzleLoadException e) {
        timerSubPresenter.stopTimer();

        gameView.showDetailedErrorAlert(
                "Puzzle Loading Error",
                "Could not load puzzle",
                e.getUserFriendlyMessage(),
                e
        );

        try {
            HomeMenuView homeMenuView = new HomeMenuView();
            HomeMenuPresenter homeMenuPresenter = new HomeMenuPresenter(homeMenuView, user);
            gameView.getScene().setRoot(homeMenuView);
            homeMenuView.getScene().getWindow().sizeToScene();
        } catch (Exception ex) {
            handleGenericException(ex);
        }
    }

    void handleGenericException(Exception e) {
        String userMessage = "An unexpected error occurred in the game.";
        if (e.getMessage() != null && !e.getMessage().isEmpty()) {
            userMessage += " Details: " + e.getMessage();
        }

        gameView.showDetailedErrorAlert(
                "Game Error",
                "Unexpected Error",
                userMessage,
                e
        );
    }

    TimerSubPresenter getTimerSubPresenter() {
        return timerSubPresenter;
    }

    VehicleSubPresenter getVehicleSubPresenter() {
        return vehicleSubPresenter;
    }
}