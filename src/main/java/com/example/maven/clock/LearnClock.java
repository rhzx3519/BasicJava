package com.example.maven.clock;

import java.time.*;

/**
 * @author ZhengHao Lou
 * @date 2020/7/8
 */
public class LearnClock {

    public static void main(String[] args) {
        LocalDate ld = LocalDate.now( ZoneId.of( "US/Eastern" ) );
        LocalDate xmasThisYear = MonthDay.of( Month.DECEMBER , 25 ).atYear( ld.getYear() );
        ZoneId earliestXmasZone = ZoneId.of( "GMT+8" ) ;
        ZonedDateTime zdtEarliestXmasThisYear = xmasThisYear.atStartOfDay( earliestXmasZone );
        Instant instantEarliestXmasThisYear = zdtEarliestXmasThisYear.toInstant();
        Clock clockEarliestXmasThisYear = Clock.fixed( instantEarliestXmasThisYear , earliestXmasZone );
    }
}
