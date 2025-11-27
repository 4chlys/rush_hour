package main.java.view.homemenuview;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.model.enums.Difficulty;
import main.java.model.game.Game;
import main.java.model.user.User;
import main.java.view.chaosview.ChaosPresenter;
import main.java.view.chaosview.ChaosView;
import main.java.view.creditsview.CreditsPresenter;
import main.java.view.creditsview.CreditsView;
import main.java.view.gameview.GamePresenter;
import main.java.view.gameview.GameView;
import main.java.view.highscoreview.HighscorePresenter;
import main.java.view.highscoreview.HighscoreView;
import main.java.view.instructionsview.InstructionsPresenter;
import main.java.view.instructionsview.InstructionsView;
import main.java.view.loginview.LoginPresenter;
import main.java.view.loginview.LoginView;
import main.java.view.selectlevelview.SelectLevelPresenter;
import main.java.view.selectlevelview.SelectLevelView;

public class HomeMenuPresenter {
    private HomeMenuView homeMenuView;
    private User user;

    public HomeMenuPresenter(HomeMenuView HomeMenuView, User user) {
        this.homeMenuView = HomeMenuView;
        this.user = user;
        addEventHandlers();
    }

    public void addEventHandlers() {
        homeMenuView.getBtnGoBack().setOnAction(actionEvent -> {
            LoginView loginView = new LoginView();
            LoginPresenter loginPresenter = new LoginPresenter(loginView);
            homeMenuView.getScene().setRoot(loginView);
            loginView.getScene().getWindow().sizeToScene();
            loginView.getScene().getWindow().centerOnScreen();
            loginView.getScene().getStylesheets().clear();
            loginView.getScene().getStylesheets().add("/stylesheet/loginview.css");
        });

        homeMenuView.getBtnPlay().setOnAction(actionEvent -> {
            GameView gameView = new GameView();
            Game game = new Game(this.user);
            GamePresenter gamePresenter = new GamePresenter(gameView, game, this.user);
            homeMenuView.getScene().setRoot(gameView);

            gameView.getScene().getStylesheets().add("/stylesheet/gameview.css");
            gameView.getScene().getWindow().setHeight(700);
            gameView.getScene().getWindow().setWidth(1200);
            gameView.getScene().getWindow().centerOnScreen();
        });

        homeMenuView.getBtnSelectLevel().setOnAction(actionEvent -> {
            SelectLevelView selectLevelView = new SelectLevelView();
            SelectLevelPresenter selectLevelPresenter = new SelectLevelPresenter(user, Difficulty.BEGINNER, selectLevelView, homeMenuView.getScene());

            Stage selectLevelStage = new Stage();

            selectLevelStage.initOwner(homeMenuView.getScene().getWindow());
            selectLevelStage.initModality(Modality.APPLICATION_MODAL);
            selectLevelStage.setScene(new Scene(selectLevelView));
            selectLevelStage.getScene().getWindow().centerOnScreen();
            selectLevelStage.getScene().getStylesheets().add("/stylesheet/selectlevelview.css");
            selectLevelStage.showAndWait();
        });

        homeMenuView.getBtnChaos().setOnAction(actionEvent -> {
            ChaosView chaosView = new ChaosView();
            ChaosPresenter chaosPresenter = new ChaosPresenter(user, chaosView, homeMenuView.getScene());

            Stage chaosStage = new Stage();

            chaosStage.initOwner(homeMenuView.getScene().getWindow());
            chaosStage.initModality(Modality.APPLICATION_MODAL);
            chaosStage.setScene(new Scene(chaosView));
            chaosStage.setX(homeMenuView.getScene().getWindow().getX() + 100);
            chaosStage.setY(homeMenuView.getScene().getWindow().getY() + 100);
            chaosStage.getScene().getWindow().centerOnScreen();
            chaosStage.getScene().getStylesheets().add("/stylesheet/chaosview.css");
            chaosStage.showAndWait();
        });

        homeMenuView.getBtnHighscores().setOnAction(actionEvent -> {
            HighscoreView highscoreView = new HighscoreView();
            HighscorePresenter highscorePresenter = new HighscorePresenter(highscoreView, user);

            Stage highscoreStage = new Stage();
            highscoreStage.initOwner(homeMenuView.getScene().getWindow());
            highscoreStage.initModality(Modality.APPLICATION_MODAL);
            highscoreStage.setScene(new Scene(highscoreView));

            String title = user != null ? "User Highscores - " + user.getUsername() : "User Highscores - Guest";
            highscoreStage.setTitle(title);

            highscoreStage.getScene().getWindow().centerOnScreen();
            highscoreStage.getScene().getStylesheets().add("/stylesheet/highscoreview.css");
            highscoreStage.showAndWait();
        });

        homeMenuView.getBtnInstructions().setOnAction(actionEvent -> {
            InstructionsView instructionsView = new InstructionsView();
            InstructionsPresenter instructionsPresenter = new InstructionsPresenter(instructionsView);

            Stage instructionsStage = new Stage();

            instructionsStage.initOwner(homeMenuView.getScene().getWindow());
            instructionsStage.initModality(Modality.APPLICATION_MODAL);
            instructionsStage.setScene(new Scene(instructionsView));
            instructionsStage.getScene().getWindow().centerOnScreen();
            instructionsStage.getScene().getStylesheets().add("/stylesheet/textviews.css");
            instructionsStage.showAndWait();
        });

        homeMenuView.getBtnCredits().setOnAction(actionEvent -> {
            CreditsView creditsView = new CreditsView();
            CreditsPresenter creditsPresenter = new CreditsPresenter(creditsView);

            Stage CreditsStage = new Stage();

            CreditsStage.initOwner(homeMenuView.getScene().getWindow());
            CreditsStage.initModality(Modality.APPLICATION_MODAL);
            CreditsStage.setScene(new Scene(creditsView));
            CreditsStage.getScene().getWindow().centerOnScreen();
            CreditsStage.getScene().getStylesheets().add("/stylesheet/textviews.css");
            CreditsStage.showAndWait();
        });
    }
}