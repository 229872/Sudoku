package pl.component;

import javafx.util.StringConverter;

public class SudokuFieldConverter extends StringConverter<Number> {
    @Override
    public String toString(Number number) {
        if (number.intValue() >= 1 && number.intValue() <= 9) {
            return String.valueOf(number.intValue());
        } else {
            return "";
        }
    }

    @Override
    public Number fromString(String s) {
        if (s.matches("^[1-9]$")) {
            return Integer.parseInt(s);
        } else {
            return 0;
        }
    }
}
