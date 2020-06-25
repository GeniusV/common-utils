package com.geniusver.util;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Paths;

/**
 * Created by GeniusV on 6/20/20.
 */
class MultiThreadFileProcessorTest {
    public static final String FILE_PATH = Paths.get(
            Thread.currentThread().getContextClassLoader().getResource(".").getPath(),
            "/temp/test.txt").toString();


    @Test
    public void test() {
        MultiThreadFileProcessor processor = new MultiThreadFileProcessor(new File(FILE_PATH), new TestFileHandler());
        processor.run();
    }


    @Test
    public void writeFile() {
        File file = new File(FILE_PATH);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        System.out.println("Writing " + file.getAbsolutePath());
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            for (int i = 0; i < 100000; i++) {
                writer.write("This is line " + i);
                writer.write(". This is line " + i);
                writer.write(". This is line " + i);
                writer.write(". This is line " + i);
                writer.write(". This is line " + i);
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}