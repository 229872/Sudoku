package pl.component.model.elements;

import pl.component.model.SudokuElement;
import pl.component.model.SudokuField;

import java.util.List;

public class SudokuColumn extends SudokuElement {

    public SudokuColumn(List<SudokuField> fields) {
        super(fields);
    }
}
