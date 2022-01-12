package by.epamtc.ivangavrilovich.threads.impl;

import by.epamtc.ivangavrilovich.entity.Matrix;
import by.epamtc.ivangavrilovich.threads.DiagonalFiller;

public class MainDiagonalFiller extends Thread implements DiagonalFiller {
    int threadName;
    Matrix matrix;

    public MainDiagonalFiller(int threadName, Matrix matrix) {
        this.threadName = threadName;
        this.matrix = matrix;
    }

    @Override
    public void run() {
        changeElements();
    }

    @Override
    public void changeElements() {
        int direction;
        int elem;
        for (int i = 0; i < matrix.getN(); i++) {
            direction = (int)Math.floor(Math.random() * 2);
            elem = (int)(Math.random() * matrix.getN());
            if (direction > 0) {
                matrix.changeMatrix(i, threadName, i, elem);
            } else {
                matrix.changeMatrix(i, threadName, elem, i);
            }
        }
    }
}
