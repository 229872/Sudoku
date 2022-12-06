package pl.component.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import pl.component.model.main.SudokuBoard;


public class FileSudokuBoardDao implements Dao<SudokuBoard> {
    public final String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() {
        try (FileInputStream inputStream = new FileInputStream(fileName);
            ObjectInput input = new ObjectInputStream(inputStream)) {
            return (SudokuBoard) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(SudokuBoard obj) {
        try (FileOutputStream outputStream = new FileOutputStream(fileName);
            ObjectOutput output = new ObjectOutputStream(outputStream)) {
            output.writeObject(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {

    }
}
