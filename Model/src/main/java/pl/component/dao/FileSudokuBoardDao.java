package pl.component.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ResourceBundle;

import pl.component.exceptions.ReadFileException;
import pl.component.exceptions.WriteFileException;
import pl.component.model.main.SudokuBoard;


public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("bundles/exceptions");
    private final String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() {
        try (FileInputStream inputStream = new FileInputStream(fileName);
            ObjectInput input = new ObjectInputStream(inputStream)) {
            return (SudokuBoard) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new ReadFileException(
                    bundle.getString("FileSudokuBoardDao.exception.read.message") ,e);
        }
    }

    @Override
    public void write(SudokuBoard obj) {
        try (FileOutputStream outputStream = new FileOutputStream(fileName);
            ObjectOutput output = new ObjectOutputStream(outputStream)) {
            output.writeObject(obj);
        } catch (IOException e) {
            throw new WriteFileException(
                    bundle.getString("FileSudokuBoardDao.exception.write.message") ,e);
        }
    }

    @Override
    public void close() throws Exception {

    }
}
