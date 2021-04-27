package com.example.maven.mvnbook;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2020/05/13
 */
@Slf4j
public class MaxValue {

    public String largestNumber(int[] nums) {
        List<String> list = Stream.of(nums).map(i -> String.valueOf(i)).collect(Collectors.toList());
        return String.join("", list);
    }

    public static void main(String[] args) {
        MaxValue mv = new MaxValue();
        int[] nums = {1, 20};
        String num = mv.largestNumber(nums);
        log.info(num);
    }

}
