package pl.component.model.elements;

import java.util.List;
import pl.component.model.main.SudokuElement;
import pl.component.model.main.SudokuField;


public class SudokuBox extends SudokuElement {

    public SudokuBox(List<SudokuField> fields) {
        super(fields);
    }

    @Override
    public SudokuBox clone()  {
        try {
            return (SudokuBox) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
