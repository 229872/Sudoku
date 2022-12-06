package pl.component.model.algorithm;

import java.io.Serializable;
import pl.component.model.main.SudokuBoard;


public interface SudokuSolver extends Serializable, Cloneable {
    void solve(SudokuBoard board);
}
