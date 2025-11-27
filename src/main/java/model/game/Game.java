package main.java.model.game;

import main.java.model.enums.Color;
import main.java.model.enums.Difficulty;
import main.java.model.exceptions.PuzzleLoadException;
import main.java.model.user.User;
import main.java.model.user.UserManager;
import main.java.model.vehicles.Vehicle;
import java.util.Arrays;

//combines all the model classes and handles game logic
public class Game {
    private Puzzle puzzle;
    private int level;
    private Difficulty difficulty;
    private Vehicle redCar;
    private int moves;
    private PuzzleManager puzzleManager;
    private User user;
    private boolean isChaos;
    private Timer timer;

    public Game(User user) {
        this.user = user;
        if (user != null) {
            this.level = user.getCurrentLevel();
            this.difficulty = user.getCurrentDifficulty();
        } else {
            this.level = 1;
            this.difficulty = Difficulty.BEGINNER;
        }
        this.puzzleManager = new PuzzleManager();
        this.puzzle = puzzleManager.getPuzzle(difficulty, level);
        this.moves = 0;
        this.redCar = puzzle.getVehicleByColor(Color.RED);
        this.isChaos = false;
        this.timer = new Timer(difficulty);
    }

    public Game(User user, int level, Difficulty difficulty) {
        this.user = user;
        this.level = level;
        this.difficulty = difficulty;
        this.puzzleManager = new PuzzleManager();
        this.puzzle = puzzleManager.getPuzzle(difficulty, level);
        this.moves = 0;
        this.redCar = puzzle.getVehicleByColor(Color.RED);
        this.isChaos = false;
        this.timer = new Timer(difficulty);
    }

    public void loadNextPuzzle() {
        this.level++;

        try {
            if (level >= 5 && difficulty == Difficulty.EXPERT) {
                this.difficulty = Difficulty.BEGINNER;
                this.level = 1;
                this.puzzle = puzzleManager.getPuzzle(difficulty, 1);
                this.moves = 0;
                this.redCar = puzzle.getVehicleByColor(Color.RED);
            }

            if (level > 4) {
                this.difficulty = difficulty.nextDifficulty();
                this.level = 1;
            }
            this.puzzle = puzzleManager.getPuzzle(difficulty, level);
            this.moves = 0;
            this.redCar = puzzle.getVehicleByColor(Color.RED);
            timer.setElapsedSeconds(0);

            if (this.user != null) {
                this.user.setCurrentLvlDiff(level, difficulty);
            }
        } catch (Exception e) {
            throw new PuzzleLoadException("Failed to load next puzzle: " + e.getMessage());
        }

    }

    public void restartPuzzle() {
        if (isNotChaos()) {
            moves = 0;
            timer.setElapsedSeconds(0);
            puzzle = puzzleManager.getPuzzle(difficulty, level);
            redCar = puzzle.getVehicleByColor(Color.RED);
        } else {startChaosGame(getDifficulty());}
    }

    public boolean isSolved() {
        return Arrays.equals(redCar.getOccupiedPositions().getFirst(), new int[]{5, 2}); //get the red car's position
    }

    public void updateHighscoreIfSolved() {
        if (isSolved() && user != null && isNotChaos()) {
            user.updateHighScore(difficulty, level, moves, timer.getElapsedSeconds());

            UserManager userManager = new UserManager();
            userManager.updateUser(user);
        }
    }

    public void incrementMoves() {
        moves++;
    }

    public void makeMove(Vehicle vehicle, int move) {
            puzzle.getBoard().moveVehicle(vehicle, move);
            if (vehicle.isMoved()) {
                incrementMoves();
                vehicle.setMoved(false);

                if (isSolved()) {
                    updateHighscoreIfSolved();
                }
            }
    }

    public void startChaosGame(Difficulty difficulty) {
        this.puzzle = puzzleManager.generateChaosPuzzle(difficulty);
        this.redCar = puzzle.getVehicleByColor(Color.RED);
        this.moves = 0;
        this.timer = new Timer(difficulty);
        this.isChaos = true;
    }

    public boolean isNotChaos() {
        return !isChaos;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public int getLevel() {
        return level;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getMoves() {
        return moves;
    }

    public User getCurrentUser() {
        return user;
    }

    public Timer getTimer() {
        return timer;
    }
}