package pl.component;

import java.util.ListResourceBundle;

public class Author_pl extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"author", "Autor: Bartłomiej Dygasiński"}
        };
    }
}
