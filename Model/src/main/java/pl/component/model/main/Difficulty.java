package pl.component.model.main;

public enum Difficulty {
    EASY(30), MEDIUM(50), HARD(60);

    private final int numberOfDeletedFields;

    Difficulty(int numberOfDeletedFields) {
        this.numberOfDeletedFields = numberOfDeletedFields;
    }

    public int getNumberOfDeletedFields() {
        return numberOfDeletedFields;
    }
}
