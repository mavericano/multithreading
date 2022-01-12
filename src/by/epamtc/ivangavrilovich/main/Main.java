package by.epamtc.ivangavrilovich.main;

import by.epamtc.ivangavrilovich.entity.Matrix;
import by.epamtc.ivangavrilovich.exceptions.WritingException;
import by.epamtc.ivangavrilovich.threads.impl.MainDiagonalFiller;
import by.epamtc.ivangavrilovich.threads.impl.SupportDiagonalFiller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Matrix matrix = Matrix.getInstance();
        int n = matrix.getN();
        int threadName = (int) (Math.random() * 100);
        ExecutorService service = Executors.newScheduledThreadPool(n * 2);
        List<MainDiagonalFiller> mainDiagonalThreads = new ArrayList<>();
        List<SupportDiagonalFiller> supportDiagonalThreads = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            mainDiagonalThreads.add(new MainDiagonalFiller(i, matrix));
        }
        for (int i = n; i < n * 2; i++) {
            supportDiagonalThreads.add(new SupportDiagonalFiller(i, matrix));
        }
        try {
            mainDiagonalThreads.forEach(service::execute);
            supportDiagonalThreads.forEach(service::execute);
            service.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            matrix.writeMatrixToFile();
        } catch (WritingException ex) {
            //LOGGER.error(ex);
            //TODO fix
        }
        service.shutdown();
    }
}
