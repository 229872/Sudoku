package pl.component.model.elements;

import pl.component.model.SudokuElement;
import pl.component.model.SudokuField;

import java.util.List;

public class SudokuBox extends SudokuElement {

    public SudokuBox(List<SudokuField> fields) {
        super(fields);
    }
}
