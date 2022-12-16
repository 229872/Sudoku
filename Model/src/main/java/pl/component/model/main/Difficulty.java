package pl.component.model.main;

import java.util.Random;
import pl.component.exceptions.WrongValueException;

public enum Difficulty {
    EASY(30), MEDIUM(50), HARD(60);

    private final int numberOfDeletedFields;

    Difficulty(int numberOfDeletedFields) {
        this.numberOfDeletedFields = numberOfDeletedFields;
    }

    public int getNumberOfDeletedFields() {
        return numberOfDeletedFields;
    }

    public void deleteFields(SudokuBoard sudokuBoard) {
        Random random = new Random();

        int i = 0;
        int rowIndex;
        int colIndex;
        while (i < numberOfDeletedFields) {
            rowIndex = random.nextInt(9);
            colIndex = random.nextInt(9);

            try {
                if (sudokuBoard.get(colIndex, rowIndex) != 0) {
                    sudokuBoard.set(colIndex, rowIndex, 0);
                    i++;
                }
            } catch (WrongValueException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
