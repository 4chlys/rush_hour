package main.java.model.enums;

public enum Difficulty {
    BEGINNER, INTERMEDIATE, ADVANCED, EXPERT;

    public Difficulty nextDifficulty() {
        return switch (this) {
            case BEGINNER -> Difficulty.INTERMEDIATE;
            case INTERMEDIATE -> Difficulty.ADVANCED;
            case ADVANCED -> Difficulty.EXPERT;
            case EXPERT -> Difficulty.BEGINNER;
        };
    }

    public Difficulty previousDifficulty() {
        return switch (this) {
            case BEGINNER -> Difficulty.EXPERT;
            case INTERMEDIATE -> Difficulty.BEGINNER;
            case ADVANCED -> Difficulty.INTERMEDIATE;
            case EXPERT -> Difficulty.ADVANCED;
        };
    }

    public String toString() {
        return switch (this) {
            case BEGINNER -> "Beginner";
            case INTERMEDIATE -> "Intermediate";
            case ADVANCED -> "Advanced";
            case EXPERT -> "Expert";
        };
    }
}
