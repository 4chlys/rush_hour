package main.java.view.chaosview;

import javafx.scene.Scene;
import main.java.model.enums.Difficulty;
import main.java.model.game.Game;
import main.java.model.user.User;
import main.java.view.gameview.GamePresenter;
import main.java.view.gameview.GameView;

public class ChaosPresenter {
    private ChaosView chaosView;
    private User user;
    private Scene currentScene;

    public ChaosPresenter(User user, ChaosView chaosView, Scene currentScene) {
        this.chaosView = chaosView;
        this.user = user;
        this.currentScene = currentScene;
        addEventHandlers();
    }


    private void addEventHandlers() {
        chaosView.getBtnDiffBeginner().setOnAction(actionEvent -> navigateToGame(Difficulty.BEGINNER));

        chaosView.getBtnDiffIntermediate().setOnAction(actionEvent -> navigateToGame(Difficulty.INTERMEDIATE));

        chaosView.getBtnDiffAdvanced().setOnAction(actionEvent -> navigateToGame(Difficulty.ADVANCED));

        chaosView.getBtnDiffExpert().setOnAction(actionEvent -> navigateToGame(Difficulty.EXPERT));
    }

    private void navigateToGame( Difficulty difficulty) {
        chaosView.getScene().getWindow().hide();
        GameView gameView = new GameView();
        Game game = new Game(this.user, 1, difficulty);
        game.startChaosGame(Difficulty.EXPERT);
        GamePresenter gamePresenter = new GamePresenter(gameView, game, this.user);
        currentScene.setRoot(gameView);
        gameView.getScene().getStylesheets().add("/stylesheet/gameview.css");
        gameView.getScene().getWindow().setHeight(700);
        gameView.getScene().getWindow().setWidth(1200);
        gameView.getScene().getWindow().centerOnScreen();
    }
}
