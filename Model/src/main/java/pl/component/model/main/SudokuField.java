package pl.component.model.main;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {
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

        return new EqualsBuilder().append(value, that.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(value).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }

    @Override
    public int compareTo(SudokuField sudokuField) {
        Objects.requireNonNull(sudokuField);
        return Integer.compare(value, sudokuField.value);
    }

    @Override
    public SudokuField clone() {
        try {
            return (SudokuField) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
