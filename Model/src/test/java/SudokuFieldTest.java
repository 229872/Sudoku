import org.junit.jupiter.api.Test;
import pl.component.model.main.SudokuField;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuFieldTest {
    private final SudokuField field1 = new SudokuField(0);
    private final SudokuField field2 = new SudokuField(0);

    @Test
    public void hashCodeTest() {
        assertEquals(field1.hashCode(), field2.hashCode());
        field1.setFieldValue(1);
        assertNotEquals(field1, field2);
    }

    @Test
    public void equalsTest() {
        assertFalse(field1.equals(null));
        assertFalse(field1.equals(String.class));
        assertEquals(field1, field1);
        assertEquals(field1, field2);

        field1.setFieldValue(1);
        assertNotEquals(field1, field2);
    }

    @Test
    public void toStringTest() {
        String expected = "[value=0]";
        assertTrue(field1.toString().contains(expected));
    }

    @Test
    public void compareToTest() {
        assertEquals(0, field1.compareTo(field2));
        field1.setFieldValue(1);
        field2.setFieldValue(5);
        assertEquals(-1, field1.compareTo(field2));
        assertEquals(1, field2.compareTo(field1));

    }

    @Test
    public void compareToShouldThrowNullPointerExceptionTest() {
        assertThrows(NullPointerException.class,
                () -> field1.compareTo(null));
    }

    @Test
    public void cloneTest() {
        SudokuField clone = field1.clone();
        assertEquals(field1, clone);
    }
}
