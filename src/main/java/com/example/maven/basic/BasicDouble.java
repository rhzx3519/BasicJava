package com.example.maven.basic;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class BasicDouble {
    public static void main(String[] args) {
        double alarmValue = 0.5d, preAlarmValue = 0.7d;
        boolean b = (Double.doubleToLongBits(alarmValue) ^ Double.doubleToLongBits(preAlarmValue)) >= 0;
        log.info("{}", b);
        b = (Double.doubleToLongBits(0.7d) ^ Double.doubleToLongBits(-0.5d)) >= 0;
        log.info("{}", b);

        Double a = null;
        b = a != null && a > 10.0;
        log.info("{}", b);
     }
}
