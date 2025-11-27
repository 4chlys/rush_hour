package main.java.view.loginview;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LoginView extends BorderPane {
    private Label lblTitle;
    private Label lblUsername;
    private Label lblPassword;
    private TextField tfUsername;
    private PasswordField pfPassword;
    private Button btnLogin;
    private Button btnSignUp;
    private Button btnGuest;
    private Label lblMessage;
    private Label lblCopyright;

    public LoginView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes() {
        this.getStyleClass().add("login-view");

        lblTitle = new Label("RUSH HOUR");
        lblTitle.setId("lblTitle");

        lblUsername = new Label("Username");
        tfUsername = new TextField();
        lblPassword = new Label("Password");
        pfPassword = new PasswordField();

        btnLogin = new Button("Login");
        btnSignUp = new Button("Sign Up");
        btnGuest = new Button("Continue as guest");

        lblMessage = new Label();
        lblMessage.setId("lblMessage");
        lblCopyright = new Label("© 2025 Casper & Romeo. All rights reserved.");
        lblCopyright.setId("lblCopyright");
    }

    private void layoutNodes() {
        VBox titleVBox = new VBox(lblTitle);
        titleVBox.setAlignment(Pos.BASELINE_CENTER);

        StackPane spUsername = new StackPane(lblUsername);
        spUsername.setAlignment(Pos.CENTER_LEFT);
        spUsername.setMaxWidth(250);

        tfUsername.setPromptText("Enter your username");

        StackPane spPassword = new StackPane(lblPassword);
        spPassword.setAlignment(Pos.CENTER_LEFT);
        spPassword.setMaxWidth(250);

        pfPassword.setPromptText("Enter your password");

        VBox textfieldVBox = new VBox();
        textfieldVBox.setAlignment(Pos.TOP_CENTER);
        textfieldVBox.setSpacing(10);
        textfieldVBox.getChildren().addAll(spUsername, tfUsername, spPassword, pfPassword);

        VBox buttonVBox = new VBox();
        buttonVBox.setAlignment(Pos.CENTER);
        buttonVBox.setSpacing(20);
        buttonVBox.getChildren().addAll(btnLogin, btnSignUp, btnGuest, lblMessage);

        VBox loginVBox = new VBox();
        loginVBox.getStyleClass().add("centerBox");
        loginVBox.setSpacing(30);
        loginVBox.setAlignment(Pos.CENTER);
        loginVBox.getChildren().addAll(titleVBox, textfieldVBox, buttonVBox);

        HBox footerHBox = new HBox(lblCopyright);
        footerHBox.setAlignment(Pos.BOTTOM_LEFT);
        footerHBox.getStyleClass().add("bottomBox");

        setCenter(loginVBox);
        setBottom(footerHBox);
    }

    Button getBtnLogin() {
        return btnLogin;
    }

    Button getBtnSignUp() {
        return btnSignUp;
    }

    Button getBtnGuest() {
        return btnGuest;
    }

    TextField getTfUsername() {
        return tfUsername;
    }

    PasswordField getPfPassword() {
        return pfPassword;
    }

    void showMessage(String message) {
        lblMessage.setAlignment(Pos.CENTER);
        lblMessage.setText(message);
        lblMessage.setTextFill(Color.RED);
    }
}
