package main.java.model.game;

import main.java.model.enums.Color;
import main.java.model.vehicles.Vehicle;

import java.util.HashMap;
import java.util.Map;

//collection of the vehicles on the board
public class Puzzle {
    private Board board;
    private Map<Color, Vehicle> vehicles;


    public Puzzle(Map<Color, Vehicle> vehicles) {
        this.board = new Board();
        this.vehicles = vehicles;
        vehicles.forEach((color, vehicle) -> {
            board.placeVehicleOnGrid(vehicle);
        });
    }

    public Puzzle copy() {
        //allows us to play on a puzzle without modifying the original puzzle

        Map<Color, Vehicle> copiedVehicles = new HashMap<>();

        for (Map.Entry<Color, Vehicle> entry : vehicles.entrySet()) {
            copiedVehicles.put(entry.getKey(), entry.getValue().copyVehicle());
        }

        return new Puzzle(copiedVehicles);
    }

    public Vehicle getVehicleByColor(Color color) {
        return vehicles.get(color);
    }

    public Board getBoard() {
        return board;
    }

    public Map<Color, Vehicle> getVehicles() {
        return vehicles;
    }
}