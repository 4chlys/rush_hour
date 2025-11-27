package main.java.model.vehicles;

import main.java.model.enums.Color;

public class Car extends Vehicle {
    private static final int CAR_SIZE = 2;


    public Car(Color color, boolean isHorizontal, int frontX, int frontY) {
        super(color, CAR_SIZE, isHorizontal, frontX, frontY);
    }


    @Override
    public Car copyVehicle() {
        return new Car(this.color, this.isHorizontal, this.occupiedPositions.getFirst()[0], this.occupiedPositions.getFirst()[1]);
    }
}