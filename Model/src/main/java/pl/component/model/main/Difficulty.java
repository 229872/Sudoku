package pl.component.model.main;

public enum Difficulty {
    EASY(20), MEDIUM(35), HARD(50);

    private final int numberOfDeletedFields;

    Difficulty(int numberOfDeletedFields) {
        this.numberOfDeletedFields = numberOfDeletedFields;
    }

    public int getNumberOfDeletedFields() {
        return numberOfDeletedFields;
    }
}
