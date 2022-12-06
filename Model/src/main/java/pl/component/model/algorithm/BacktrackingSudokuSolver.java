package pl.component.model.algorithm;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import pl.component.exceptions.WrongValueException;
import pl.component.model.main.SudokuBoard;



public class BacktrackingSudokuSolver implements SudokuSolver {
    @Override
    public void solve(SudokuBoard board) {
        try {
            solveBoard(board);
        } catch (WrongValueException ignored) {
            // do nothing
        }
    }

    private boolean solveBoard(SudokuBoard board) throws WrongValueException {
        List<Integer> randomNumbers = Arrays.asList(1,2,3,4,5,6,7,8,9);
        Collections.shuffle(randomNumbers);

        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int column = 0; column < board.getBoardSize(); column++) {
                if (board.get(column, row) == 0) {
                    for (Integer k : randomNumbers) {
                        board.set(column, row, k);
                        boolean validationResult = board.getRow(row).verify()
                                && board.getColumn(column).verify()
                                && board.getBox(column, row).verify();
                        if (validationResult && solveBoard(board)) {
                            return true;
                        }
                        board.set(column, row, 0);
                    }
                    return false;
                }
            }
        }
        return true;
    }

}
