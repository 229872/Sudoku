package pl.component.model.elements;

import java.util.List;
import pl.component.model.main.SudokuElement;
import pl.component.model.main.SudokuField;

public class SudokuRow extends SudokuElement {

    public SudokuRow(List<SudokuField> fields) {
        super(fields);
    }

    @Override
    public SudokuRow clone() {
        try {
            return (SudokuRow) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
