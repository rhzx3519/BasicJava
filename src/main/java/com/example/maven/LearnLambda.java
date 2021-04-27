package com.example.maven;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @auther louzh
 * @create 2020/04/13
 */
@Data
class Person {
  public String name;

  public Person(String name) {
    this.name = name;
  }
}


@Slf4j
public class LearnLambda {
    public static void main(String[] args) throws Exception {
        List<String> names = Arrays.asList("Mike", "jim");
        Stream<Person> stream = names.stream().map(Person::new);
        List<Person> people = stream.collect(Collectors.toList());
        repeat(10, i->System.out.println("Countdown: " + (9-i)));
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
            .parallel()
            .forEachOrdered(e->{
                System.out.println(Thread.currentThread().getName() + ":" + e);
            });

        File file = new File("/Users/louzhenghao/Downloads/EXT902_2GU_20200409.CSV");
        //FileInputStream fileInputStream = new FileInputStream(file);
        Reader in = new InputStreamReader(new FileInputStream(file));
        in.close();

        Map<String, String> FTP_NAME_MAP = new HashMap<>();
        FTP_NAME_MAP.put("1", "IB");
        Map res = FTP_NAME_MAP.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> {
            String configName = entry.getValue();
            return configName;
        }));
        System.out.println("file");

        Map<String, Long> map = new HashMap<>(2, 1);
        List<String> lists = Lists.newArrayList();
        log.info("{}", map.size());
        log.info("{}", lists.isEmpty());


    }

    public static void repeat(int n, IntConsumer action) {
        for (int i = 0; i < n; i++) action.accept(i);
    }
}
