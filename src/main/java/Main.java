package main.java;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.java.view.loginview.LoginPresenter;
import main.java.view.loginview.LoginView;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("/fonts/RacelineDemo.otf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Montserrat-Bold.ttf"), 12);

        LoginView loginView = new LoginView();
        LoginPresenter loginPresenter = new LoginPresenter(loginView);

        Scene loginScene = new Scene(loginView, 1000, 800);
        loginScene.getStylesheets().add("/stylesheet/loginview.css");

        stage.setScene(loginScene);
        loginPresenter.addWindowEventHandlers();

        stage.setMinHeight(800);
        stage.setMinWidth(1000);
        stage.setTitle("Rush Hour");
        stage.centerOnScreen();
        stage.show();
    }
}
