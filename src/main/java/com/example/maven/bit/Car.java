package com.example.maven.bit;

import lombok.Data;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ZhengHao Lou
 * @date 2020/8/27
 */
@Component
@Data
public class Car {
    private Seat seat;
    private Wheel wheel;

    @Autowired
    public Car(Seat seat, Wheel wheel) {
        this.seat = seat;
        this.wheel = wheel;
    }
}
