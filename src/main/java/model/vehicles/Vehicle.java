package main.java.model.vehicles;


import main.java.model.enums.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle implements IsMovable {
    protected Color color;
    protected int size;
    protected boolean isHorizontal;
    protected List<int[]> occupiedPositions;
    protected boolean isMoved;


    public Vehicle(Color color, int size, boolean isHorizontal, int frontX, int frontY) {
        this.color = color;
        this.size = size;
        this.isHorizontal = isHorizontal;
        this.occupiedPositions = new ArrayList<>();
        this.isMoved = false;

        initializePositions(frontX, frontY);
    }


    public void initializePositions(int frontX, int frontY) {
        occupiedPositions.clear();
        for (int i = 0; i < size; i++) {
            if (isHorizontal) {
                occupiedPositions.add(new int[]{frontX - i, frontY});
            } else {
                occupiedPositions.add(new int[]{frontX, frontY - i});
            }

        }
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public boolean isMoved() {
        return isMoved;
    }

    public void setMoved(boolean isMoved) {
        this.isMoved = isMoved;
    }

    @Override
    public void moveHorizontal(int deltaX) {
        int frontX = occupiedPositions.getFirst()[0];
        if (isHorizontal) {
            for (int[] position : occupiedPositions) {
                position[0] += deltaX;
            }
        }
        if (occupiedPositions.getFirst()[0] != frontX) {
            isMoved = true;
        }
    }

    @Override
    public void moveVertical(int deltaY) {
        int frontY = occupiedPositions.getFirst()[1];
        if (!isHorizontal) {
            for (int[] position : occupiedPositions) {
                position[1] += deltaY;
            }
        }
        if (occupiedPositions.getFirst()[1] != frontY) {
            isMoved = true;
        }
    }

    public abstract Vehicle copyVehicle();


    public int getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    public List<int[]> getOccupiedPositions() {
        return occupiedPositions;
    }
}
