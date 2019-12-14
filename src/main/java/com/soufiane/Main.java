package com.soufiane;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String... args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // For windows provide different path like: c:\\temp\\file.log
        String filePath = args[0];
        LogFileReader tailF = new LogFileReader(filePath, 2000, args[1]);

        // Start running log file tailer on file.log file
        executor.execute(tailF);
    }
}
