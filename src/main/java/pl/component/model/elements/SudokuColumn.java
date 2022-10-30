package pl.component.model.elements;

import java.util.List;
import pl.component.model.SudokuElement;
import pl.component.model.SudokuField;

public class SudokuColumn extends SudokuElement {

    public SudokuColumn(List<SudokuField> fields) {
        super(fields);
    }
}
