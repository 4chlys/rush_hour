package main.java.model.vehicles;

import main.java.model.enums.Color;

public class Truck extends Vehicle {
    private static final int TRUCK_SIZE = 3;


    public Truck(Color color, boolean isHorizontal, int frontX, int frontY) {
        super(color, TRUCK_SIZE, isHorizontal, frontX, frontY);
    }


    @Override
    public Truck copyVehicle() {
        return new Truck(this.color, this.isHorizontal, this.occupiedPositions.getFirst()[0], this.occupiedPositions.getFirst()[1]);
    }
}