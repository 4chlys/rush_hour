package main.java.view.instructionsview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class InstructionsView extends StackPane {
    private Text instructionsText;

    public InstructionsView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes() {
        this.getStyleClass().add("view");

        instructionsText = new Text();
        instructionsText.getStyleClass().add("text");
        instructionsText.setTextAlignment(TextAlignment.LEFT);
        instructionsText.wrappingWidthProperty().bind(widthProperty().multiply(0.8));
    }

    private void layoutNodes() {
        setAlignment(Pos.CENTER);
        setPrefSize(800, 600);


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(instructionsText);
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox textContainer = new VBox(instructionsText);
        textContainer.setPadding(new Insets(20));
        textContainer.setAlignment(Pos.TOP_CENTER);
        scrollPane.setContent(textContainer);

        getChildren().addAll(scrollPane);
    }

    public void setInstructionsText(String text) {
        instructionsText.setText(text);
    }
}
