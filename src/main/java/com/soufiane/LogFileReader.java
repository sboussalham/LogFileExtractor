package com.soufiane;

import java.io.File;
import java.io.RandomAccessFile;

public class LogFileReader implements Runnable {
    private boolean debug = false;

    private int runEveryNSeconds = 2000;
    private long lastKnownPosition = 0;
    private boolean shouldIRun = true;
    private String searchedText = "";
    private File file = null;
    private static int counter = 0;

    public LogFileReader(String myFile, int myInterval, String searchedText) {
        file = new File(myFile);
        this.runEveryNSeconds = myInterval;
        this.searchedText = searchedText;
    }

    private void printLine(String message) {
        System.out.println(message);
    }

    private void printLineIfContains(String message) {
        if (message.contains(searchedText)) {
            System.out.println(message);
        }
    }

    public void stopRunning() {
        shouldIRun = false;
    }

    public void run() {
        try {
            while (shouldIRun) {
                Thread.sleep(runEveryNSeconds);
                long fileLength = file.length();
                if (fileLength > lastKnownPosition) {

                    // Reading and writing file
                    RandomAccessFile readWriteFileAccess = new RandomAccessFile(file, "rw");
                    readWriteFileAccess.seek(lastKnownPosition);
                    String line = null;
                    while ((line = readWriteFileAccess.readLine()) != null) {
                        this.printLineIfContains(line);
                        counter++;
                    }
                    lastKnownPosition = readWriteFileAccess.getFilePointer();
                    readWriteFileAccess.close();
                } else {
                    if (debug)
                        this.printLine("Hmm.. Couldn't found new line after line # " + counter);
                }
            }
        } catch (Exception e) {
            stopRunning();
        }
        if (debug)
            this.printLine("Exit the program...");
    }
}
