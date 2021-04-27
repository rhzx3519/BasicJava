package com.example.maven.guava;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2020/06/03
 */
@Slf4j
public class LearnIO {

    public byte[] read(File file) throws IOException {
       return Files.asByteSource(file).read();
    }

    public static void main(String[] args) throws IOException {
        Files.touch(new File("/tmp/tmp.txt"));
        File file = new File("/tmp/tmp.txt");
        LearnIO learnIO = new LearnIO();
        learnIO.read(file);
        file.delete();
        file.delete();
        log.info("{}", file.exists());
        log.info("1\\n2");

        String s = null;
        log.info("{}", s);
    }
}
