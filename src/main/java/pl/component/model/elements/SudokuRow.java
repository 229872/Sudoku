package pl.component.model.elements;

import pl.component.model.SudokuElement;
import pl.component.model.SudokuField;

import java.util.List;

public class SudokuRow extends SudokuElement {

    public SudokuRow(List<SudokuField> fields) {
        super(fields);
    }
}
