package main.java.view.gameview;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class GameSubView extends StackPane {
    private GridPane boardGrid;
    private static final int GRID_SIZE = 6;
    private static final int CELL_SIZE = 90;

    public GameSubView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes() {
        boardGrid = new GridPane();
        this.getStyleClass().add("game-sub-view");
    }

    private void layoutNodes() {
        setupGrid();

        double backgroundSize = GRID_SIZE * CELL_SIZE + 1.2 * CELL_SIZE;
        ImageView gameBackground = new ImageView(new Image("/images/game_background.png"));
        gameBackground.setFitWidth(backgroundSize);
        gameBackground.setFitHeight(backgroundSize);
        gameBackground.setPreserveRatio(false);
        gameBackground.setTranslateX(5);

        getChildren().addAll(gameBackground, boardGrid);
        setAlignment(boardGrid, Pos.CENTER);
        setAlignment(gameBackground, Pos.CENTER);

    }

    private void setupGrid() {
        boardGrid.setAlignment(Pos.CENTER);

        for (int i = 0; i < GRID_SIZE; i++) {
            ColumnConstraints colConst = new ColumnConstraints(CELL_SIZE);
            boardGrid.getColumnConstraints().add(colConst);

            RowConstraints rowConst = new RowConstraints(CELL_SIZE);
            boardGrid.getRowConstraints().add(rowConst);
        }
    }

    int getCellSize() {
        return CELL_SIZE;
    }

    GridPane getBoardGrid() {
        return boardGrid;
    }

}