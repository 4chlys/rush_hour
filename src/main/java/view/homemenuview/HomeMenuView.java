package main.java.view.homemenuview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;


public class HomeMenuView extends BorderPane {
    private Label lblTitle;
    private Button btnGoBack;
    private Button btnPlay;
    private Button btnSelectLevel;
    private Button btnChaos;
    private Button btnHighscores;
    private Button btnInstructions;
    private Button btnCredits;

    public HomeMenuView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes() {
        this.getStyleClass().add("home-menu-view");

        lblTitle = new Label("HOME MENU");
        lblTitle.setId("lblTitle");

        btnGoBack = new Button("Go Back");
        btnGoBack.setId("btnGoBack");
        btnPlay = new Button("Play");
        btnSelectLevel = new Button("Select Level");
        btnChaos = new Button("Chaos Mode");
        btnHighscores = new Button("Highscores");
        btnInstructions = new Button("Instructions");
        btnCredits = new Button("Credits");
    }

    private void layoutNodes() {
        VBox titleBox = new VBox(lblTitle);
        titleBox.setAlignment(Pos.BASELINE_CENTER);

        VBox buttonVBox = new VBox();
        buttonVBox.setAlignment(Pos.CENTER);
        buttonVBox.setSpacing(20);
        buttonVBox.getChildren().addAll(btnPlay, btnSelectLevel, btnChaos, btnHighscores, btnInstructions, btnCredits);

        HBox bottomBox = new HBox(btnGoBack);
        bottomBox.getStyleClass().add("bottomBox");
        bottomBox.setAlignment(Pos.BOTTOM_LEFT);

        VBox centerBox = new VBox();
        centerBox.getStyleClass().add("centerBox");
        centerBox.setSpacing(30);
        centerBox.setPadding(new Insets(30));
        centerBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().addAll(titleBox, buttonVBox);

        setCenter(centerBox);
        setBottom(bottomBox);
    }

    Button getBtnGoBack() {
        return btnGoBack;
    }

    Button getBtnPlay() {
        return btnPlay;
    }

    Button getBtnSelectLevel() {
        return btnSelectLevel;
    }

    Button getBtnChaos() {
        return btnChaos;
    }

    Button getBtnHighscores() {
        return btnHighscores;
    }

    Button getBtnInstructions() {
        return btnInstructions;
    }

    Button getBtnCredits() {
        return btnCredits;
    }
}