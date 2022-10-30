package pl.component.model.elements;

import java.util.List;
import pl.component.model.SudokuElement;
import pl.component.model.SudokuField;


public class SudokuBox extends SudokuElement {

    public SudokuBox(List<SudokuField> fields) {
        super(fields);
    }
}
