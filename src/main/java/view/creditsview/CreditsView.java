package main.java.view.creditsview;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class CreditsView extends StackPane {
    private Text creditsText;

    public CreditsView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes() {
        this.getStyleClass().add("view");

        creditsText = new Text();
        creditsText.getStyleClass().add("text");
        creditsText.setTextAlignment(TextAlignment.CENTER);
    }

    private void layoutNodes() {
        setAlignment(Pos.CENTER);
        setPrefSize(800, 600);

        getChildren().addAll(creditsText);
    }

    public void setCreditsText(String text) {
        creditsText.setText(text);
    }

    public Text getCreditsText() {
        return creditsText;
    }
}
