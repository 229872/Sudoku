package pl.component.dao;

import pl.component.model.main.SudokuBoard;
import java.io.*;

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
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void write(SudokuBoard obj) {
        try (FileOutputStream outputStream = new FileOutputStream(fileName);
            ObjectOutput output = new ObjectOutputStream(outputStream)) {
            output.writeObject(obj);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void close() throws Exception {

    }
}
