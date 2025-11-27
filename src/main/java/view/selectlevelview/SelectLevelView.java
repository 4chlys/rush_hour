package main.java.view.selectlevelview;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class SelectLevelView extends BorderPane {
    private Label lblTitle;
    private Button btnLevel1;
    private Button btnLevel2;
    private Button btnLevel3;
    private Button btnLevel4;
    private Button[][] levelButtons;
    private Button btnPrevious;
    private Button btnNext;

    public SelectLevelView() {
        initializeNodes();
        layoutNodes();
    }


    private void initializeNodes() {
        this.getStyleClass().add("select-level-view");
        lblTitle = new Label("DIFFICULTY LEVEL");
        btnLevel1 = new Button("Level 1");
        btnLevel2 = new Button("Level 2");
        btnLevel3 = new Button("Level 3");
        btnLevel4 = new Button("Level 4");
        levelButtons = new Button[][]{{btnLevel1, btnLevel3}, {btnLevel2, btnLevel4}};
        btnPrevious = new Button("Previous");
        btnNext = new Button("Next");
        btnNext.getStyleClass().add("switch-button");
        btnPrevious.getStyleClass().add("switch-button");
    }


    private void layoutNodes() {
        VBox titleVBox = new VBox(lblTitle);
        titleVBox.setAlignment(Pos.BASELINE_CENTER);

        GridPane buttonGrid = new GridPane();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                buttonGrid.add(levelButtons[i][j], i, j);
                levelButtons[i][j].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            }
        }
        buttonGrid.setAlignment(Pos.CENTER);
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);


        HBox switchDifficultyHBox = new HBox();
        switchDifficultyHBox.getStyleClass().add("switch-difficulty-hbox");
        switchDifficultyHBox.setAlignment(Pos.CENTER);
        Region whiteSpace = new Region();
        whiteSpace.setMinWidth(60);
        whiteSpace.setMaxWidth(60);
        switchDifficultyHBox.getChildren().addAll(btnPrevious, whiteSpace, btnNext);

        VBox contentBox = new VBox();
        contentBox.getStyleClass().add("contentBox");
        contentBox.setSpacing(30);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.getChildren().addAll(titleVBox, buttonGrid, switchDifficultyHBox);

        setCenter(contentBox);
    }

    void setLblTitle(String title) {
        lblTitle.setText(title);
    }

    Button getBtnLevel1() {
        return btnLevel1;
    }

    Button getBtnLevel2() {
        return btnLevel2;
    }

    Button getBtnLevel3() {
        return btnLevel3;
    }

    Button getBtnLevel4() {
        return btnLevel4;
    }

    Button getBtnNext() {
        return btnNext;
    }

    Button getBtnPrevious() {
        return btnPrevious;
    }
}

