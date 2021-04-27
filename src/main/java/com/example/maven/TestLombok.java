package com.example.maven;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @auther louzh
 * @create 2020/04/13
 */
@Slf4j
public class TestLombok {
  @Builder
  @Data
  public static class Item {
    private String name;
    private int pos;
  }

  public static void main(String[] args) {
   Item.ItemBuilder itemBuilder = Item.builder().name("Mike").pos(0);
    List<String> strs = Arrays.asList("A", "B");
    strs.forEach(str -> {
        if ("A".equals(str)) return;
        log.info(str);
    });
    Map<String, String> strsMap = strs.stream().collect(Collectors.toMap(String::toLowerCase, Function.identity()));
    log.info("{}", strsMap);

    strs = strs.stream().filter(s -> s!="A").collect(Collectors.toList());
    log.info("{}", strs);

    List<Integer> integers = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
        integers.add(i);
    }
    int n = integers.size();
    int MAX_NUMBER = 10;
    int limit = (n + MAX_NUMBER - 1) / MAX_NUMBER;
    List<List<Integer>> splitList = Stream.iterate(0, i -> i + 1).limit(limit).parallel().map(a -> integers.stream().skip(a * MAX_NUMBER).limit(MAX_NUMBER).parallel().collect(Collectors.toList())).collect(Collectors.toList());
    log.info("splitList: {}", splitList);
  }
}
