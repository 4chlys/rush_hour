package main.java.view.gameview;


import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

public class GameView extends BorderPane {
    private GameSubView gameSubView;
    private PuzzleSubView puzzleSubView;
    private MenuBar menuBar;
    private Menu accountMenu;
    private Menu aboutUsMenu;
    private Menu selectLevelMenu;
    private MenuItem logoutMenuItem;
    private MenuItem showUsernameMenuItem;
    private MenuItem returnToLoginMenuItem;
    private MenuItem creditsMenuItem;
    private MenuItem beginnerLevelMenuItem;
    private MenuItem intermediateLevelMenuItem;
    private MenuItem advancedLevelMenuItem;
    private MenuItem expertLevelMenuItem;

    public GameView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes() {
        this.getStyleClass().add("game-view");
        gameSubView = new GameSubView();
        puzzleSubView = new PuzzleSubView();

        menuBar = new MenuBar();
        menuBar.setViewOrder(-1);

        accountMenu = new Menu("Account");
        logoutMenuItem = new MenuItem("Log Out");
        showUsernameMenuItem = new MenuItem("Show Username");
        returnToLoginMenuItem = new MenuItem("Return to Login Screen");
        accountMenu.getItems().addAll(showUsernameMenuItem, logoutMenuItem, returnToLoginMenuItem);

        aboutUsMenu = new Menu("About Us");
        creditsMenuItem = new MenuItem("Credits");
        aboutUsMenu.getItems().add(creditsMenuItem);

        selectLevelMenu = new Menu("Select Level");
        beginnerLevelMenuItem = new MenuItem("Beginner Levels");
        intermediateLevelMenuItem = new MenuItem("Intermediate Levels");
        advancedLevelMenuItem = new MenuItem("Advanced Levels");
        expertLevelMenuItem = new MenuItem("Expert Levels");
        selectLevelMenu.getItems().addAll(beginnerLevelMenuItem, intermediateLevelMenuItem, advancedLevelMenuItem, expertLevelMenuItem);

        menuBar.getMenus().addAll(accountMenu, aboutUsMenu, selectLevelMenu);
    }

    private void layoutNodes() {
        HBox gameLayout = new HBox();
        gameLayout.getChildren().addAll(puzzleSubView, gameSubView);
        gameLayout.setAlignment(Pos.CENTER);
        gameLayout.setSpacing(100);

        setTop(menuBar);
        setCenter(gameLayout);
    }

    public void showDetailedErrorAlert(String title, String header, String message, Throwable exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("Technical details:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }


    public MenuItem getLogoutMenuItem() {
        return logoutMenuItem;
    }

    public MenuItem getShowUsernameMenuItem() {
        return showUsernameMenuItem;
    }

    public MenuItem getReturnToLoginMenuItem() {
        return returnToLoginMenuItem;
    }

    public MenuItem getCreditsMenuItem() {
        return creditsMenuItem;
    }

    public MenuItem getBeginnerLevelMenuItem() {
        return beginnerLevelMenuItem;
    }

    public MenuItem getIntermediateLevelMenuItem() {
        return intermediateLevelMenuItem;
    }

    public MenuItem getAdvancedLevelMenuItem() {
        return advancedLevelMenuItem;
    }

    public MenuItem getExpertLevelMenuItem() {
        return expertLevelMenuItem;
    }

    GridPane getBoardGrid() {
        return gameSubView.getBoardGrid();
    }

    GridPane getPuzzleGrid() {
        return puzzleSubView.getPuzzleGrid();
    }

    double getCellSize() {
        return gameSubView.getCellSize();
    }

    PuzzleSubView getPuzzleSubView() {
        return puzzleSubView;
    }
}