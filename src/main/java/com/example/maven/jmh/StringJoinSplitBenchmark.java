package com.example.maven.jmh;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.List;

/**
 * @author ZhengHao Lou
 * @date 2020/7/12
 */
public class StringJoinSplitBenchmark {
    private static final List<String> nameList = Lists.newArrayList("Tom", "Jerry", "Vincent");
    private static final char delimeter = ',';
    private static final String str = "Tom, Jerry, Vincent";
    /**
     * 测试：Guava Strings join
     */
    @Benchmark
    public void test1() {
        print(Joiner.on(delimeter).join(nameList));
    }

    /**
     * 测试：apache common StringUtils join
     */
    @Benchmark
    public void test2() {
        print(StringUtils.join(nameList, delimeter));
    }

    /**
     * 测试: Guava String Splitter
     */
    @Benchmark
    public void test3() {
        print(Splitter.on(delimeter)
                .split(str));
    }

    /**
     * 测试: apache common StringUtils split
     */
    @Benchmark
    public void test4() {
        print(StringUtils.split(str, delimeter));
    }

    private void print(String str) {}

    private void print(String[] strs) {}

    private void print(Iterable<String> strs) {}
}
