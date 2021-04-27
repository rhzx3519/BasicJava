package com.example.maven.stream;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @auther louzh
 * @create 2020/04/18
 */

@Builder
@Data
class User {
    private Integer id;
    private String name;
    private int score;
    private String country;
}

@Slf4j
public class TestStream {

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);

        List<User> users = new ArrayList<>();
        numbers = numbers.stream().filter(i -> i!=1).collect(Collectors.toList());
        log.info("{}", numbers);

        users.add(User.builder()
                .id(1)
                .name("Jim")
                .score(2)
                .country("CN")
                .build());
        users.add(User.builder()
                .id(2)
                .name("Tom")
                .score(5)
                .country("CN")
                .build());
        users.add(User.builder()
                .id(3)
                .name("Rick")
                .score(100)
                .country("USA")
                .build());

        log.info("{}", users);
        Map<String, Double> countScoreMap = users.stream()
                .collect(Collectors.groupingBy(User::getCountry, Collectors.averagingInt(User::getScore)));
        //Map<String, Long> countScoreMap = users.stream()
        //        .collect(Collectors.groupingBy(User::getCountry, Collectors.counting()));
        log.info("{}", countScoreMap);
        Map<String, Long> countMap = users.stream()
                .collect(Collectors.groupingBy(User::getCountry, Collectors.counting()));

        Map<String, Long> map = Maps.newHashMap();
        map.put("Tom", 1L);
        long number = map.get("Jim");
        log.info("{}", number);

    }

}
