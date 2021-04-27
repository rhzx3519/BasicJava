package com.example.maven.date;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZhengHao Lou
 * @date 2020/10/16
 */
@Slf4j
public class Main {

    private Date toDate(LocalDate localDate) {
        return java.util.Date.from(localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public void test() {
        List<Date> dateList = Lists.newArrayList(toDate(LocalDate.now().minusDays(1)),
                toDate(LocalDate.now().minusDays(3)), toDate(LocalDate.now().minusDays(2)));
        List<Date> sortedDateList = dateList.stream().sorted().collect(Collectors.toList());
        log.info("{}", sortedDateList);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.test();
    }

}
