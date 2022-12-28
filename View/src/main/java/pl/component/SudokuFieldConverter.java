package pl.component;

import javafx.util.StringConverter;

public class SudokuFieldConverter extends StringConverter<Integer> {
    @Override
    public String toString(Integer integer) {
        if (integer >= 1 && integer <= 9) {
            return String.valueOf(integer);
        } else {
            return "";
        }
    }

    @Override
    public Integer fromString(String s) {
        if (s.matches("^[1-9]$")) {
            return Integer.parseInt(s);
        } else {
            return 0;
        }
    }
}
