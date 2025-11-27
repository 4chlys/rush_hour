package main.java.view.selectlevelview;

import javafx.scene.Scene;
import main.java.model.enums.Difficulty;
import main.java.model.game.Game;
import main.java.model.user.User;
import main.java.view.gameview.GamePresenter;
import main.java.view.gameview.GameView;

public class SelectLevelPresenter {
    private User user;
    private SelectLevelView selectLevelView;
    private Scene currentScene;
    private Difficulty difficulty;

    public SelectLevelPresenter(User user, Difficulty difficulty, SelectLevelView selectLevelView, Scene currentScene) {
        this.selectLevelView = selectLevelView;
        this.selectLevelView.setLblTitle(difficulty.toString());
        this.user = user;
        this.difficulty = difficulty;
        this.currentScene = currentScene;
        addEventHandlers();
    }


    private void addEventHandlers() {
        selectLevelView.getBtnLevel1().setOnAction(actionEvent -> navigateToGame(1));

        selectLevelView.getBtnLevel2().setOnAction(actionEvent -> navigateToGame(2));

        selectLevelView.getBtnLevel3().setOnAction(actionEvent -> navigateToGame(3));

        selectLevelView.getBtnLevel4().setOnAction(actionEvent -> navigateToGame(4));

        selectLevelView.getBtnNext().setOnAction(actionEvent -> {
            SelectLevelView newSelectLevelView = new SelectLevelView();
            SelectLevelPresenter newSelectLevelPresenter = new SelectLevelPresenter(user, difficulty.nextDifficulty(), newSelectLevelView, currentScene);
            selectLevelView.getScene().setRoot(newSelectLevelView);
            newSelectLevelView.getScene().getWindow().sizeToScene();
        });

        selectLevelView.getBtnPrevious().setOnAction(actionEvent -> {
            SelectLevelView newSelectLevelView = new SelectLevelView();
            SelectLevelPresenter newSelectLevelPresenter = new SelectLevelPresenter(this.user, difficulty.previousDifficulty(), newSelectLevelView, currentScene);
            selectLevelView.getScene().setRoot(newSelectLevelView);
            newSelectLevelView.getScene().getWindow().sizeToScene();
        });
    }

    private void navigateToGame(int level) {
        selectLevelView.getScene().getWindow().hide();
        GameView gameView = new GameView();
        Game game = new Game(this.user, level, difficulty);
        GamePresenter gamePresenter = new GamePresenter(gameView, game, this.user);
        currentScene.setRoot(gameView);
        gameView.getScene().getStylesheets().clear();
        gameView.getScene().getStylesheets().add("/stylesheet/gameview.css");
        gameView.getScene().getWindow().setHeight(700);
        gameView.getScene().getWindow().setWidth(1200);
        gameView.getScene().getWindow().centerOnScreen();
    }
}
