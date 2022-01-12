package by.epamtc.ivangavrilovich.entity;

import by.epamtc.ivangavrilovich.exceptions.PropertiesException;
import by.epamtc.ivangavrilovich.exceptions.WritingException;
import by.epamtc.ivangavrilovich.main.MatrixWriter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Matrix {
    private int n;
    private volatile int[][] matrix;
    private StringBuffer buffer = new StringBuffer();
    private final Properties properties = new Properties();

    public static Matrix getInstance() {
        return MatrixContainer.MATRIX_INSTANCE;
    }

    private Matrix() {
        InputStream a = this.getClass().getClassLoader().getResourceAsStream("matrix.properties");
        try {
            properties.load(a);
        } catch (IOException e) {
            throw new PropertiesException("Error loading properties file", e);
        }
        n = Integer.parseInt(properties.getProperty("n"));
        matrix = new int[n][n];
    }

    private static class MatrixContainer {
        public static final Matrix MATRIX_INSTANCE = new Matrix();
    }

    public void changeMatrix(int i, int threadName, int x, int y) {
        changeMatrix(i, i, threadName, x, y);
    }

    public synchronized void changeMatrix(int i, int j, int threadName, int x, int y) {
        matrix[i][j] = threadName;
        matrix[x][y] = threadName;
        writeMatrix(i, j, threadName);
    }

    private void writeMatrix(int i, int j, int threadName) {
        for (int[] row : matrix) {
            for (int elem : row) {
                buffer.append(elem).append(" ");
            }
            buffer.append("\n");
        }
        buffer.append(String.format("Row %d sum is %d", i, countRowSum(i))).append("\n");
        buffer.append(String.format("Column %d sum is %d", j, countColSum(j))).append("\n");
        buffer.append(String.format("Thread %d", threadName)).append("\n");
        buffer.append("\n");
    }

    public void writeMatrixToFile() throws WritingException {
        String path = properties.getProperty("resultFilePath");
        MatrixWriter writer = new MatrixWriter(path);
        writer.write(buffer.toString());
        writer.close();
    }

    private int countRowSum(int i) {
        int res = 0;
        for (int elem : matrix[i]) {
            res += elem;
        }
        return res;
    }

    private int countColSum(int j) {
        int res = 0;
        for (int[] row : matrix) {
            res += row[j];
        }
        return res;
    }

    public int getN() {
        return n;
    }
}
