package main.java.view.gameview;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class PuzzleSubView extends VBox {
    private GridPane puzzleGrid;
    private Button btnPause;
    private Button btnGoBack;
    private Button btnRestart;
    private Label lblDifficulty;
    private Label lblLevel;
    private Label lblMoves;
    private Label lblTimer;
    private static final int GRID_SIZE = 6;
    private static final int CELL_SIZE = 25;

    public PuzzleSubView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes() {
        this.getStyleClass().add("puzzle-sub-view");
        puzzleGrid = new GridPane();
        btnPause = new Button("Pause");
        btnGoBack = new Button("Go back");
        btnRestart = new Button("Restart");
        lblDifficulty = new Label("Difficulty");
        lblLevel = new Label("Level");
        lblMoves = new Label("Moves");
        lblTimer = new Label("Time: 00:00");
    }

    private void layoutNodes() {
        setupGrid();

        double backgroundSize = GRID_SIZE * CELL_SIZE + 1.4 * CELL_SIZE;
        ImageView background = new ImageView(new Image("/images/game_background.png"));
        background.setFitWidth(backgroundSize);
        background.setFitHeight(backgroundSize);
        background.setPreserveRatio(false);

        StackPane puzzlePane = new StackPane();
        puzzlePane.getChildren().addAll(background, puzzleGrid);
        StackPane.setAlignment(background, Pos.CENTER);
        StackPane.setAlignment(puzzleGrid, Pos.CENTER);

        Region puzzleGridRegion = new Region();
        puzzleGridRegion.setPrefHeight(30);

        VBox lblVBox = new VBox();
        lblVBox.setAlignment(Pos.CENTER);
        lblVBox.getChildren().addAll(lblDifficulty, lblLevel, lblMoves, lblTimer);
        lblVBox.setSpacing(20);

        VBox buttonVBox = new VBox();
        buttonVBox.setAlignment(Pos.CENTER);
        buttonVBox.setSpacing(20);
        buttonVBox.getChildren().addAll(lblVBox, btnPause, btnRestart, btnGoBack);

        getChildren().addAll(puzzlePane, puzzleGridRegion, buttonVBox);

        setAlignment(Pos.CENTER);
        setSpacing(20);
    }

    private void setupGrid() {
        puzzleGrid.setAlignment(Pos.CENTER);

        for (int i = 0; i < GRID_SIZE; i++) {
            ColumnConstraints colConst = new ColumnConstraints(CELL_SIZE);
            puzzleGrid.getColumnConstraints().add(colConst);

            RowConstraints rowConst = new RowConstraints(CELL_SIZE);
            puzzleGrid.getRowConstraints().add(rowConst);
        }
    }

    GridPane getPuzzleGrid() {
        return puzzleGrid;
    }

    Button getBtnPause() {
        return btnPause;
    }

    Button getBtnGoBack() {
        return btnGoBack;
    }

    Button getBtnRestart() {
        return btnRestart;
    }

    int getCellSize() {
        return CELL_SIZE;
    }

    Label getLblDifficulty() {
        return lblDifficulty;
    }

    Label getLblLevel() {
        return lblLevel;
    }

    Label getLblMoves() {
        return lblMoves;
    }

    Label getLblTimer() {
        return lblTimer;
    }

    void setBtnPause(String btnTitle) {
        this.btnPause.setText(btnTitle);
    }
}