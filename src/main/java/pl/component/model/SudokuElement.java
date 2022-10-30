package pl.component.model;

import java.util.*;

public abstract class SudokuElement {
    private final List<SudokuField> fields;

    public SudokuElement(List<SudokuField> fields) {
        this.fields = fields;
    }


    public boolean verify() {
        Set<Integer> checker = new HashSet<>(9);
        int zerosCounter = 0;
        for (SudokuField value : fields) {
            if (value.getFieldValue() != 0) {
                checker.add(value.getFieldValue());
            } else {
                zerosCounter++;
            }
        }
        return checker.size() == fields.size() - zerosCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuElement that = (SudokuElement) o;
        return Objects.equals(fields, that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fields);
    }
}
