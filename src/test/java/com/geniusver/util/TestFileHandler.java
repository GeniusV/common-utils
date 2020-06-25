package com.geniusver.util;

/**
 * Created by GeniusV on 6/20/20.
 */
public class TestFileHandler implements FileTextHandler{


    @Override
    public void handleLine(String line) {
        System.out.println(Thread.currentThread().getName() + ": " + line);
        try {
            Thread.sleep(300L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
