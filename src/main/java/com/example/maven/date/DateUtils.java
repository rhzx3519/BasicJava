package com.example.maven.date;

import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2020/05/08
 */
@Slf4j
public class DateUtils {

    public static void main(String[] args) throws InterruptedException {
        ZoneId newYorkZoneId = ZoneId.of("America/New_York");
        LocalDate date = LocalDate.now(newYorkZoneId);
        LocalDateTime dateTime = LocalDateTime.now(newYorkZoneId);
        log.info("{}", dateTime);
        log.info("{}", LocalDateTime.now());
        log.info("{}", LocalDate.now().atTime(10, 0));
        List<Integer> list = Lists.newArrayList(1, 2, 3);
        int sum = list.stream().reduce(0, (a, b) -> a+b);
        log.info("{}", sum);

        String pattern = "dd-MMM-yyyy";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.US);
        String dateFormat = date.format(dateTimeFormatter);
        log.info(dateFormat);
        log.info(LocalDate.of(1970, 01, 01).format(dateTimeFormatter));

        Double f = 123.0;
        log.info(String.format("%s", f));

        String zonedDateTime = LocalDateTime.now().atZone(ZoneId.of("Asia/Shanghai")).toString();
        log.info(zonedDateTime);


        zonedDateTime = LocalDateTime.now().atZone(ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        log.info(zonedDateTime);

        LocalDate now = LocalDate.now();
        Thread.sleep(1000L);
        LocalDate now2 = LocalDate.now();
        log.info("{}", now2.isBefore(now));
    }

}
