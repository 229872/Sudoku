package pl.component.model.main;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public abstract class SudokuElement implements Serializable, Cloneable {
    private final List<SudokuField> fields;

    public SudokuElement(List<SudokuField> fields) {
        this.fields = fields;
    }


    public boolean verify() {
        Set<Integer> checker = new HashSet<>(9);
        int zerosCounter = 0;
        for (SudokuField value : fields) {
            if (value.getFieldValue() != 0) {
                checker.add(value.getFieldValue());
            } else {
                zerosCounter++;
            }
        }
        return checker.size() == fields.size() - zerosCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuElement that = (SudokuElement) o;

        return new EqualsBuilder().append(fields, that.fields).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(fields).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fields", fields)
                .toString();
    }
}
