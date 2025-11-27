package main.java.model.game;

import main.java.model.vehicles.Vehicle;

//generates the grid and handles moves on the board
public class Board {
    private static final int BOARD_SIZE = 6;
    private Vehicle[][] grid;

    public Board() {
        this.grid = new Vehicle[BOARD_SIZE][BOARD_SIZE];
    }

    private boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE;
    }

    private boolean isValidPlacement(Vehicle vehicle) {
        for (int[] position : vehicle.getOccupiedPositions()) {
            int x = position[0];
            int y = position[1];
            if (isOutOfBounds(x, y) || grid[x][y] != null) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidMove(Vehicle vehicle, int move) {
        //checks if a position is occupied by another vehicle or is out of bounds when moved

        for (int[] position : vehicle.getOccupiedPositions()) {
            for (int i = 1; i <= Math.abs(move); i++) {
                int deltaX;
                int deltaY;

                if (move < 0) {
                    deltaX = vehicle.isHorizontal() ? -i : 0;
                    deltaY = vehicle.isHorizontal() ? 0 : -i;
                } else {
                    deltaX = vehicle.isHorizontal() ? i : 0;
                    deltaY = vehicle.isHorizontal() ? 0 : i;
                }

                int oldX = position[0];
                int oldY = position[1];
                int newX = oldX + deltaX;
                int newY = oldY + deltaY;

                if (isOutOfBounds(newX, newY) || grid[newX][newY] != null && grid[newX][newY] != vehicle) {
                    return false;
                }
            }
        }
        return true;
    }

    public void placeVehicleOnGrid(Vehicle vehicle) {
        if (isValidPlacement(vehicle)) {
            for (int[] position : vehicle.getOccupiedPositions()) {
                grid[position[0]][position[1]] = vehicle;
            }
        }
    }

    private void clearVehicle(Vehicle vehicle) {
        for (int[] position : vehicle.getOccupiedPositions()) {
            grid[position[0]][position[1]] = null;
        }
    }

    public void moveVehicle(Vehicle vehicle, int move) {
        if (isValidMove(vehicle, move)) {
            clearVehicle(vehicle);

            if (vehicle.isHorizontal()) {
                vehicle.moveHorizontal(move);
            } else {
                vehicle.moveVertical(move);
            }

            placeVehicleOnGrid(vehicle);
        }
    }
}