package main.java.model.exceptions;

public class PuzzleLoadException extends RuntimeException {
    private String userFriendlyMessage;

    public PuzzleLoadException(String message) {
        super(message);
        this.userFriendlyMessage = generateUserFriendlyMessage(message);
    }

    public PuzzleLoadException(String message, Throwable cause) {
        super(message, cause);
        this.userFriendlyMessage = generateUserFriendlyMessage(message);
    }

    public PuzzleLoadException(Throwable cause) {
        super(cause);
        this.userFriendlyMessage = generateUserFriendlyMessage(cause.getMessage());
    }

    public String getUserFriendlyMessage() {
        return userFriendlyMessage;
    }

    private String generateUserFriendlyMessage(String errorMessage) {
        if (errorMessage == null) {
            return "An unknown error occurred while loading the puzzle.";
        }
        return "An error occurred while loading the puzzle: " + errorMessage;
    }
}