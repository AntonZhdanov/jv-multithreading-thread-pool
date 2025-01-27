package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        List<Future<String>> futures = new ArrayList<>();
        int numThreads = 20;
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < numThreads; i++) {
            Callable<String> task = new MyThread();
            Future<String> future = executorService.submit(task);
            futures.add(future);
        }

        for (Future<String> future : futures) {
            try {
                String result = future.get();
                logger.info(result);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("some went wrong!");
            }
        }
        executorService.shutdown();
    }
}
