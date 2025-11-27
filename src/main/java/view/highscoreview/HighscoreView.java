package main.java.view.highscoreview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import main.java.model.enums.Difficulty;

public class HighscoreView extends BorderPane {
    private Label lblTitle;
    private Button btnClose;
    private TabPane tabPane;
    private Tab beginnerTab;
    private Tab intermediateTab;
    private Tab advancedTab;
    private Tab expertTab;
    private VBox beginnerVBox;
    private VBox intermediateVBox;
    private VBox advancedVBox;
    private VBox expertVBox;

    public HighscoreView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes() {
        this.getStyleClass().add("highscore-view");

        lblTitle = new Label("HIGHSCORES");
        lblTitle.setId("lblTitle");

        btnClose = new Button("Close");
        btnClose.setId("btnClose");

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        beginnerTab = new Tab("Beginner");
        intermediateTab = new Tab("Intermediate");
        advancedTab = new Tab("Advanced");
        expertTab = new Tab("Expert");

        beginnerVBox = new VBox(10);
        beginnerVBox.setPadding(new Insets(20));
        beginnerVBox.setAlignment(Pos.TOP_CENTER);

        intermediateVBox = new VBox(10);
        intermediateVBox.setPadding(new Insets(20));
        intermediateVBox.setAlignment(Pos.TOP_CENTER);

        advancedVBox = new VBox(10);
        advancedVBox.setPadding(new Insets(20));
        advancedVBox.setAlignment(Pos.TOP_CENTER);

        expertVBox = new VBox(10);
        expertVBox.setPadding(new Insets(20));
        expertVBox.setAlignment(Pos.TOP_CENTER);
    }

    private void layoutNodes() {
        VBox titleBox = new VBox(lblTitle);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(20, 0, 20, 0));

        beginnerTab.setContent(beginnerVBox);
        intermediateTab.setContent(intermediateVBox);
        advancedTab.setContent(advancedVBox);
        expertTab.setContent(expertVBox);

        tabPane.getTabs().addAll(beginnerTab, intermediateTab, advancedTab, expertTab);

        HBox bottomBox = new HBox(btnClose);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));

        setTop(titleBox);
        setCenter(tabPane);
        setBottom(bottomBox);
        setPrefWidth(600);
        setPrefHeight(500);
    }

    public GridPane createScoreGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        Label levelHeader = new Label("Level");
        Label movesHeader = new Label("Moves");
        Label timeHeader = new Label("Time");
        Label dateHeader = new Label("Date");

        levelHeader.getStyleClass().add("score-header");
        movesHeader.getStyleClass().add("score-header");
        timeHeader.getStyleClass().add("score-header");
        dateHeader.getStyleClass().add("score-header");

        grid.add(levelHeader, 0, 0);
        grid.add(movesHeader, 1, 0);
        grid.add(timeHeader, 2, 0);
        grid.add(dateHeader, 3, 0);

        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        ColumnConstraints col4 = new ColumnConstraints();

        col1.setPercentWidth(15);
        col2.setPercentWidth(25);
        col3.setPercentWidth(25);
        col4.setPercentWidth(35);

        grid.getColumnConstraints().addAll(col1, col2, col3, col4);

        return grid;
    }

    public Label createNoRecordsLabel() {
        Label noRecords = new Label("No highscores recorded yet.");
        noRecords.getStyleClass().add("no-records");
        return noRecords;
    }

    public Button getBtnClose() {
        return btnClose;
    }

    public VBox getBeginnerVBox() {
        return beginnerVBox;
    }

    public VBox getIntermediateVBox() {
        return intermediateVBox;
    }

    public VBox getAdvancedVBox() {
        return advancedVBox;
    }

    public VBox getExpertVBox() {
        return expertVBox;
    }

    public void setActiveTab(Difficulty difficulty) {
        switch (difficulty) {
            case BEGINNER:
                tabPane.getSelectionModel().select(0);
                break;
            case INTERMEDIATE:
                tabPane.getSelectionModel().select(1);
                break;
            case ADVANCED:
                tabPane.getSelectionModel().select(2);
                break;
            case EXPERT:
                tabPane.getSelectionModel().select(3);
                break;
        }
    }
}
