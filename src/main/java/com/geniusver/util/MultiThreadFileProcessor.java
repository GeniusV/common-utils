package com.geniusver.util;

import java.io.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by GeniusV on 6/20/20.
 *
 * Simple demo of multithreading process large file with time-consuming logic.
 */
public class MultiThreadFileProcessor implements Runnable {
    private ExecutorService externalThreadPool;
    private BlockingQueue<String> blockingQueue;
    private String fileEncoding = "UTF-8";
    private volatile AtomicBoolean readFinished = new AtomicBoolean(false);
    private int readerTimeout = 10;
    private int consumerTimeout = 10;
    private int queueSize = 1000;
    private int consumerSize = 100;
    private CountDownLatch consumerCountDownLatch;
    private File file;
    private FileTextHandler fileTextHandler;

    public MultiThreadFileProcessor(File file, FileTextHandler fileTextHandler) {
        this.file = file;
        this.fileTextHandler = fileTextHandler;
    }

    @Override
    public void run() {
        blockingQueue = new LinkedBlockingDeque<>(queueSize);
        consumerCountDownLatch = new CountDownLatch(consumerSize);
        ExecutorService executorService = externalThreadPool;
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(consumerSize + 1, consumerSize + 1,
                    0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        }
        executorService.submit(new Reader());
        for (int i = 0; i < consumerSize; i++) {
            executorService.submit(new Consumer());
        }

        try {
            consumerCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private class Reader implements Runnable {
        @Override
        public void run() {
            System.out.println("Reading " + file);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), fileEncoding))) {
                while (null != bufferedReader.readLine()) {
                    blockingQueue.offer(bufferedReader.readLine(), readerTimeout, TimeUnit.SECONDS);
                }
            } catch (IOException e) {
                throw new MultiThradFileProcessException("Exception when read file: " + file.getAbsolutePath(), e);
            } catch (InterruptedException e) {
                throw new MultiThradFileProcessException("Timeout when reader add line to queue.", e);
            } finally {
                readFinished.compareAndSet(false, true);
            }
        }
    }


    private class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                String line;
                while (true) {
                    line = blockingQueue.poll();
                    if (line == null) {
                        if (readFinished.get()) {
                            break;
                        } else {
                            line = blockingQueue.poll(consumerTimeout, TimeUnit.SECONDS);
                        }
                    }

                    fileTextHandler.handleLine(line);
                }
            } catch (InterruptedException e) {
                throw new MultiThradFileProcessException("Timeout when try read from queue");
            } finally {
                consumerCountDownLatch.countDown();
            }
        }
    }


}
