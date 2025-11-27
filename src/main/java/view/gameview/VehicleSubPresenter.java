package main.java.view.gameview;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import main.java.model.exceptions.PuzzleLoadException;
import main.java.model.game.Game;
import main.java.model.vehicles.Vehicle;

import java.io.InputStream;

public class VehicleSubPresenter {
    private GameView gameView;
    private Game game;
    private GamePresenter gamePresenter;
    private double mousePressX;
    private double mousePressY;
    private double initialTranslateX;
    private double initialTranslateY;
    private Vehicle currentVehicle;

    public VehicleSubPresenter(Game game, GameView gameView, GamePresenter gamePresenter) {
        this.game = game;
        this.gameView = gameView;
        this.gamePresenter = gamePresenter;
    }

    void createVehicleNodes() {
        for (Vehicle vehicle : game.getPuzzle().getVehicles().values()) {
            Node vehicleNode = createVehicleNode(vehicle, gameView.getCellSize());
            int startCol = vehicle.getOccupiedPositions().getLast()[0];
            int startRow = vehicle.getOccupiedPositions().getLast()[1];
            int colSpan = vehicle.isHorizontal() ? vehicle.getSize() : 1;
            int rowSpan = vehicle.isHorizontal() ? 1 : vehicle.getSize();

            gameView.getBoardGrid().add(vehicleNode, startCol, startRow, colSpan, rowSpan);
        }
    }

    void addVehicleEventHandlers() {
        for (Node vehicleNode : gameView.getBoardGrid().getChildren()) {
            if (vehicleNode.getUserData() instanceof Vehicle) {
                vehicleNode.setOnMousePressed(e -> onMousePressed(e, vehicleNode));
                vehicleNode.setOnMouseDragged(e -> onMouseDragged(e, vehicleNode));
                vehicleNode.setOnMouseReleased(this::onMouseReleased);
            }
        }
    }

    void createPuzzleGridVehicles() {
        for (Vehicle vehicle : game.getPuzzle().getVehicles().values()) {
            Node vehicleNode = createVehicleNode(vehicle, gameView.getPuzzleSubView().getCellSize());
            int startCol = vehicle.getOccupiedPositions().getLast()[0];
            int startRow = vehicle.getOccupiedPositions().getLast()[1];
            int colSpan = vehicle.isHorizontal() ? vehicle.getSize() : 1;
            int rowSpan = vehicle.isHorizontal() ? 1 : vehicle.getSize();

            gameView.getPuzzleGrid().add(vehicleNode, startCol, startRow, colSpan, rowSpan);
        }
    }

    private Node createVehicleNode(Vehicle vehicle, double cellSize) {
        try {
            String vehicleType = vehicle.getSize() == 2 ? "car" : "truck";
            String orientation = vehicle.isHorizontal() ? "horizontal" : "vertical";
            String imagePath = "/images/" + vehicle.getColor().toString().toLowerCase() +
                    "_" + vehicleType + "_" + orientation + ".png";

            try (InputStream imageStream = getClass().getResourceAsStream(imagePath)) {
                if (imageStream == null) {
                    throw new PuzzleLoadException("Could not find vehicle image: " + imagePath);
                }

                ImageView vehicleImage = new ImageView(new Image(imageStream));
                vehicleImage.getStyleClass().add("vehicle");
                vehicleImage.setFitWidth(vehicle.isHorizontal() ? vehicle.getSize() * cellSize : cellSize);
                vehicleImage.setFitHeight(vehicle.isHorizontal() ? cellSize : vehicle.getSize() * cellSize);
                vehicleImage.setPreserveRatio(false);
                vehicleImage.setSmooth(true);
                vehicleImage.setCache(true);
                vehicleImage.setUserData(vehicle);
                return vehicleImage;
            }
        } catch (Exception e) {
            gamePresenter.handleGenericException(e);
            Rectangle placeholder = new Rectangle(cellSize, cellSize);
            placeholder.setFill(Color.valueOf(vehicle.getColor().toString()));
            placeholder.setUserData(vehicle);
            return placeholder;
        }
    }

    private void moveVehicle(Vehicle vehicle, int move) {
        try {
            game.makeMove(vehicle, move);

            // update labels after moving
            gameView.getPuzzleSubView().getLblLevel().setText("Level " + game.getLevel());
            gameView.getPuzzleSubView().getLblMoves().setText("Moves: " + game.getMoves());
            gameView.getPuzzleSubView().getLblDifficulty().setText("Difficulty: " + game.getDifficulty());
            gamePresenter.getTimerSubPresenter().updateTimerDisplay();

            gameView.getBoardGrid().getChildren().clear();
            createVehicleNodes();
            addVehicleEventHandlers();

            if (game.isSolved()) {
                gamePresenter.getTimerSubPresenter().stopTimer();

                int finalMoveCount = game.getMoves();

                Node redCarNode = null;
                for (Node node : gameView.getBoardGrid().getChildren()) {
                    if (node.getUserData() instanceof Vehicle v) {
                        if (v.getColor() == main.java.model.enums.Color.RED) {
                            redCarNode = node;
                            break;
                        }
                    }
                }

                if (redCarNode != null) {
                    TranslateTransition exitTransition = getTranslateTransition(redCarNode, finalMoveCount);
                    exitTransition.play();
                } else {
                    gamePresenter.displayVictoryMessage(finalMoveCount);
                    gamePresenter.loadNextPuzzleAndUpdateView();
                }
            }
        } catch (PuzzleLoadException e) {
            gamePresenter.handlePuzzleLoadException(e);
        } catch (Exception e) {
            gamePresenter.handleGenericException(e);
        }
    }

    private TranslateTransition getTranslateTransition(Node redCarNode, int finalMoveCount) {
        TranslateTransition exitTransition = new TranslateTransition(Duration.seconds(1.5), redCarNode);

        double exitDistance = 2 * gameView.getCellSize();
        exitTransition.setByX(exitDistance);

        exitTransition.setOnFinished(event -> {
            gamePresenter.displayVictoryMessage(finalMoveCount);
            gamePresenter.loadNextPuzzleAndUpdateView();
        });
        return exitTransition;
    }

    private void onMousePressed(MouseEvent e, Node vehicleNode) {
        try {
            mousePressX = e.getSceneX();
            mousePressY = e.getSceneY();
            currentVehicle = (Vehicle) vehicleNode.getUserData();
            initialTranslateX = vehicleNode.getTranslateX();
            initialTranslateY = vehicleNode.getTranslateY();
        } catch (Exception ex) {
            gamePresenter.handleGenericException(ex);
        }
    }

    private void onMouseDragged(MouseEvent e, Node vehicleNode) {
        try {
            double deltaX = e.getSceneX() - mousePressX;
            double deltaY = e.getSceneY() - mousePressY;
            if (currentVehicle.isHorizontal()) {
                vehicleNode.setTranslateX(initialTranslateX + deltaX);
            } else {
                vehicleNode.setTranslateY(initialTranslateY + deltaY);
            }
        } catch (Exception ex) {
            gamePresenter.handleGenericException(ex);
        }
    }

    private void onMouseReleased(MouseEvent e) {
        try {
            double deltaX = e.getSceneX() - mousePressX;
            double deltaY = e.getSceneY() - mousePressY;
            int deltaMove = currentVehicle.isHorizontal() ?
                    roundToNearestCell(deltaX / gameView.getCellSize()) :
                    roundToNearestCell(deltaY / gameView.getCellSize());

            moveVehicle(currentVehicle, deltaMove);
        } catch (Exception ex) {
            gamePresenter.handleGenericException(ex);
        }
    }

    private int roundToNearestCell(double value) {
        return (int) ((value > 0) ? Math.floor(value + 0.5) : Math.ceil(value - 0.5));
    }
}