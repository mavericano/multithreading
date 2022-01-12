package by.epamtc.ivangavrilovich.main;

import by.epamtc.ivangavrilovich.exceptions.WritingException;

import java.io.FileWriter;
import java.io.IOException;

public class MatrixWriter {
    private FileWriter fileWriter;

    public MatrixWriter(String path) throws WritingException {
        try {
            fileWriter = new FileWriter(path, true);
            System.out.println(path);
        } catch (IOException e) {
            throw new WritingException("Error while writing to path" + path, e);
        }
    }

    public void write(String result) throws WritingException {
        try {
            fileWriter.write(result);
        } catch (IOException e) {
            throw new WritingException("Error while writing to result file", e);
        }
    }

    public void close() throws WritingException {
        if (fileWriter != null) {
            try {
                fileWriter.close();
            } catch (IOException e) {
                throw new WritingException("Error while closing writer", e);
            }
        }
    }
}
