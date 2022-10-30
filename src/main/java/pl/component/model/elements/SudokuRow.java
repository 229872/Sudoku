package pl.component.model.elements;

import java.util.List;
import pl.component.model.SudokuElement;
import pl.component.model.SudokuField;

public class SudokuRow extends SudokuElement {

    public SudokuRow(List<SudokuField> fields) {
        super(fields);
    }
}
