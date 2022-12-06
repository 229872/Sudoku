package pl.component.model.elements;

import java.util.List;
import pl.component.model.main.SudokuElement;
import pl.component.model.main.SudokuField;

public class SudokuColumn extends SudokuElement {

    public SudokuColumn(List<SudokuField> fields) {
        super(fields);
    }

    @Override
    public SudokuColumn clone() {
        try {
            return (SudokuColumn) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
