package main.java.view.chaosview;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ChaosView extends BorderPane {
    private Label lblTitle;
    private Button btnDiffBeginner;
    private Button btnDiffIntermediate;
    private Button btnDiffAdvanced;
    private Button btnDiffExpert;

    public ChaosView() {
        initializeNodes();
        layoutNodes();
    }


    private void initializeNodes() {
        this.getStyleClass().add("chaosview");
        lblTitle = new Label("CHAOS");
        btnDiffBeginner = new Button("BEGINNER");
        btnDiffIntermediate = new Button("INTERMEDIATE");
        btnDiffAdvanced = new Button("ADVANCED");
        btnDiffExpert = new Button("EXPERT");
    }


    private void layoutNodes() {
        VBox titleVBox = new VBox(lblTitle);
        titleVBox.setAlignment(Pos.CENTER);


        VBox diffButtonBox = new VBox(btnDiffBeginner, btnDiffIntermediate, btnDiffAdvanced, btnDiffExpert);
        diffButtonBox.getStyleClass().add("diffButtonBox");
        diffButtonBox.setAlignment(Pos.CENTER);
        diffButtonBox.setSpacing(20);

        VBox contentBox = new VBox();
        contentBox.getStyleClass().add("contentBox");
        contentBox.setSpacing(10);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.getChildren().addAll(titleVBox, diffButtonBox);

        setCenter(contentBox);
    }

    Button getBtnDiffBeginner() {
        return btnDiffBeginner;
    }

    Button getBtnDiffIntermediate() {
        return btnDiffIntermediate;
    }

    Button getBtnDiffAdvanced() {
        return btnDiffAdvanced;
    }

    Button getBtnDiffExpert() {
        return btnDiffExpert;
    }

}
