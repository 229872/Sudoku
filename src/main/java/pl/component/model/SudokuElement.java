package pl.component.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class SudokuElement {
    private final List<SudokuField> fields;

    public SudokuElement(List<SudokuField> fields) {
        this.fields = fields;
    }


    private boolean verify() {
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
}
