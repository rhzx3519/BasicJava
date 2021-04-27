package com.example.maven.io;

import com.sun.org.apache.xpath.internal.compiler.FunctionTable;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2020/06/07
 */
@Slf4j
public class ScannerTest {
    public static void main(String[] args)
    {
        final Pattern EVERYTHING_PATTERN = Pattern.compile("\\A");

        System.out.println("请输入若干单词，以空格作为分隔");
        Scanner sc = new Scanner(System.in);
        if(sc.hasNextLine())
        {
            //System.out.println("键盘输入的内容是："
            //
            //        + sc.next());
        }
        String result = sc.useDelimiter(EVERYTHING_PATTERN).next();
        log.info(result);
        System.out.println("执行吗");
    }
}
