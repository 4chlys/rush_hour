package main.java.model.game;

import main.java.model.enums.Color;
import main.java.model.enums.Difficulty;
import main.java.model.exceptions.PuzzleLoadException;
import main.java.model.vehicles.Car;
import main.java.model.vehicles.Truck;
import main.java.model.vehicles.Vehicle;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

//reads and stores all the puzzle from textfiles per difficulty
public class PuzzleManager {
    private List<Puzzle> beginnerPuzzles;
    private List<Puzzle> intermediatePuzzles;
    private List<Puzzle> advancedPuzzles;
    private List<Puzzle> expertPuzzles;

    private static final String BEGINNER_PUZZLES_FILE = "/puzzles/beginnerpuzzles.txt";
    private static final String INTERMEDIATE_PUZZLES_FILE = "/puzzles/intermediatepuzzles.txt";
    private static final String ADVANCED_PUZZLES_FILE = "/puzzles/advancedpuzzles.txt";
    private static final String EXPERT_PUZZLES_FILE = "/puzzles/expertpuzzles.txt";

    public PuzzleManager() {
        beginnerPuzzles = new ArrayList<>();
        intermediatePuzzles = new ArrayList<>();
        advancedPuzzles = new ArrayList<>();
        expertPuzzles = new ArrayList<>();
        initializePuzzleManager();
    }

    private void initializePuzzleManager() {
        try {
            loadPuzzlesFromResource(BEGINNER_PUZZLES_FILE, beginnerPuzzles);
            loadPuzzlesFromResource(INTERMEDIATE_PUZZLES_FILE, intermediatePuzzles);
            loadPuzzlesFromResource(ADVANCED_PUZZLES_FILE, advancedPuzzles);
            loadPuzzlesFromResource(EXPERT_PUZZLES_FILE, expertPuzzles);
        } catch (IOException e) {
            throw new PuzzleLoadException("Failed to load puzzle files", e);
        }
    }

    private void loadPuzzlesFromResource(String resourcePath, List<Puzzle> puzzleList) throws IOException {
        //reads the whole file and recognizes the puzzles

        InputStream inputStream = getClass().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new PuzzleLoadException("Could not find resource: " + resourcePath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String puzzleLine;
            Map<Color, Vehicle> vehicles = new HashMap<>();

            while ((puzzleLine = reader.readLine()) != null) {
                puzzleLine = puzzleLine.trim(); //trims empty space

                if (puzzleLine.isEmpty() || puzzleLine.startsWith("#")) {
                    continue;
                }

                if (puzzleLine.equals("PUZZLE")) {
                    if (!vehicles.isEmpty()) {
                        puzzleList.add(new Puzzle(vehicles));
                        vehicles = new HashMap<>(); //new vehicles for the next puzzle
                    }
                    continue;
                }

                Vehicle vehicle = parseVehicleLine(puzzleLine);
                if (vehicle != null) {
                    vehicles.put(vehicle.getColor(), vehicle);
                }
            }

            if (!vehicles.isEmpty()) {
                puzzleList.add(new Puzzle(vehicles));
            }
        }
    }

    private Vehicle parseVehicleLine(String puzzleLine) {
        //reads a line from the textfile and translates it to a vehicle

        String[] lineSegments = puzzleLine.split(";");
        if (lineSegments.length < 5) {
            return null;
        }

        try {
            String type = lineSegments[0].trim();
            Color color = Color.valueOf(lineSegments[1].trim());
            boolean isHorizontal = Boolean.parseBoolean(lineSegments[2].trim());
            int x = Integer.parseInt(lineSegments[3].trim());
            int y = Integer.parseInt(lineSegments[4].trim());

            if (type.equalsIgnoreCase("CAR")) {
                return new Car(color, isHorizontal, x, y);
            } else if (type.equalsIgnoreCase("TRUCK")) {
                return new Truck(color, isHorizontal, x, y);
            }
        } catch (IllegalArgumentException e) {
            throw new PuzzleLoadException("Error parsing vehicle puzzleLine: " + puzzleLine); //if car attributes are wrong in the file
        }

        return null;
    }

    public Puzzle generateChaosPuzzle(Difficulty difficulty) {
        //moves a certain amount of vehicles in an existing puzzle to make it random
        Random random = new Random();
        Puzzle chaosPuzzle = getPuzzle(difficulty, random.nextInt(4) + 1).copy();
        for (Map.Entry<Color, Vehicle> entry : chaosPuzzle.getVehicles().entrySet()) {
            Color color = entry.getKey();
            Vehicle vehicle = entry.getValue();

            if (color.equals(Color.RED)) continue;

            for (int i = 0; i < random.nextInt(10); i++) {
                chaosPuzzle.getBoard().moveVehicle(vehicle, random.nextInt(-3, 3 ));
            }
        }
        return chaosPuzzle;
    }

    public Puzzle getPuzzle(Difficulty difficulty, int level) {
        try {
            Puzzle originalPuzzle;
            return switch (difficulty) {
                case BEGINNER -> {
                    originalPuzzle = beginnerPuzzles.get(level - 1);
                    yield originalPuzzle.copy();
                }
                case INTERMEDIATE -> {
                    originalPuzzle = intermediatePuzzles.get(level - 1);
                    yield originalPuzzle.copy();
                }
                case ADVANCED -> {
                    originalPuzzle = advancedPuzzles.get(level - 1);
                    yield originalPuzzle.copy();
                }
                case EXPERT -> {
                    originalPuzzle = expertPuzzles.get(level - 1);
                    yield originalPuzzle.copy();
                }
            };
        } catch (IndexOutOfBoundsException e) {
            throw new PuzzleLoadException("Puzzle level " + level + " does not exist for difficulty " + difficulty);
        }
    }
}