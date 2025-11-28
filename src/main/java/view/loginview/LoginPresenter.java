package main.java.view.loginview;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import main.java.model.exceptions.UserFileException;
import main.java.model.exceptions.UserLoginException;
import main.java.model.exceptions.UserRegistrationException;
import main.java.model.user.User;
import main.java.model.user.UserManager;
import main.java.view.homemenuview.HomeMenuPresenter;
import main.java.view.homemenuview.HomeMenuView;

public class LoginPresenter {
    private LoginView loginView;
    private UserManager userManager;

    public LoginPresenter(LoginView loginView) throws UserFileException {
        this.loginView = loginView;

        try {
            userManager = new UserManager();
        } catch (UserFileException e) {
            showErrorAlert("User Data Error", "Failed to load user data",
                    "The application cannot continue: " + e.getMessage());

            throw e;
        }

        addEventHandlers();
    }

    private void addEventHandlers() {
        loginView.getBtnSignUp().setOnAction(actionEvent ->
                handleSignUp(loginView.getTfUsername().getText(), loginView.getPfPassword().getText()));

        loginView.getBtnLogin().setOnAction(actionEvent ->
                handleLogin(loginView.getTfUsername().getText(), loginView.getPfPassword().getText()));

        loginView.getBtnGuest().setOnAction(actionEvent -> {
            try {
                HomeMenuView homeMenuView = new HomeMenuView();
                HomeMenuPresenter homeMenuPresenter = new HomeMenuPresenter(homeMenuView, null);

                loginView.getScene().setRoot(homeMenuView);
                homeMenuView.getScene().getWindow().sizeToScene();
                homeMenuView.getScene().getWindow().centerOnScreen();
                homeMenuView.getScene().getStylesheets().clear();
                homeMenuView.getScene().getStylesheets().add("/stylesheet/homemenuview.css");
            } catch (Exception e) {
                showErrorAlert("Navigation Error", "Failed to open home menu",
                        "There was a problem navigating to the home menu: " + e.getMessage());
            }
        });
    }

    public void addWindowEventHandlers() {
        loginView.getScene().getWindow().setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("By exiting the game, your current puzzle will be reset!");
            alert.setContentText("Are you sure you want to exit?");
            alert.setTitle("Warning!");
            alert.getButtonTypes().clear();
            ButtonType no = new ButtonType("No");
            ButtonType yes = new ButtonType("Yes");
            alert.getButtonTypes().addAll(yes, no);

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("/stylesheet/alertview.css");
            dialogPane.getStyleClass().add("close-game");

            alert.showAndWait();
            if (alert.getResult() == null || alert.getResult().equals(no)) {
                event.consume();
            }
        });
    }

    public void handleSignUp(String username, String password) {
        try {
            boolean success = userManager.signUp(username, password);
            if (success) {
                loginView.showMessage("Registration successful. Please log in.");
            } else {
                loginView.showMessage("Username already exists.");
            }
        } catch (UserRegistrationException e) {
            loginView.showMessage(e.getMessage());
        } catch (UserFileException e) {
            showErrorAlert("File Error", "Failed to save user data",
                    "There was a problem saving your account: " + e.getMessage());
        } catch (Exception e) {
            showErrorAlert("Registration Error", "Unexpected error during registration",
                    "An unexpected error occurred: " + e.getMessage());
        }
    }

    public void handleLogin(String username, String password) {
        try {
            User user = userManager.login(username, password);
            if (user != null) {
                HomeMenuView homeMenuView = new HomeMenuView();
                HomeMenuPresenter homeMenuPresenter = new HomeMenuPresenter(homeMenuView, user);

                loginView.getScene().setRoot(homeMenuView);
                homeMenuView.getScene().getWindow().sizeToScene();
                homeMenuView.getScene().getWindow().centerOnScreen();
                homeMenuView.getScene().getStylesheets().clear();
                homeMenuView.getScene().getStylesheets().add("/stylesheet/homemenuview.css");
            } else {
                loginView.showMessage("Incorrect username or password.");
            }
        } catch (UserLoginException e) {
            loginView.showMessage(e.getMessage());
        } catch (UserFileException e) {
            showErrorAlert("File Error", "Failed to access user data",
                    "There was a problem accessing your account: " + e.getMessage());
        } catch (Exception e) {
            showErrorAlert("Login Error", "Unexpected error during login",
                    "An unexpected error occurred: " + e.getMessage());
        }
    }

    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}