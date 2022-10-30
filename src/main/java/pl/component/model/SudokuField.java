package pl.component.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Objects;

public class SudokuField {
    private int value;
    private final PropertyChangeSupport supportListener;

    public SudokuField(int value) {
        this.value = value;
        supportListener = new PropertyChangeSupport(this);
    }

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value) {
        int lastValue = this.value;
        this.value = value;
        supportListener.firePropertyChange("value", lastValue, value);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        supportListener.addPropertyChangeListener(listener);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuField that = (SudokuField) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
