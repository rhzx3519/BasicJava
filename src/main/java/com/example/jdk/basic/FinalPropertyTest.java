package com.example.jdk.basic;

import lombok.extern.slf4j.Slf4j;


/**
 * @author ZhengHao Lou
 * @date 2021/01/13
 */
@Slf4j
public class FinalPropertyTest {
    private static class Wheel {
        private String str;
    }

    private static class Car {
        protected Wheel wheel = new Wheel();
    }

    private static class FuleCar extends Car {

    }

    private static class EvCar extends  Car {

    }

    public static void main(String[] args) {
        FuleCar fuleCar =  new FuleCar();
        EvCar evCar = new EvCar();
        assert fuleCar.wheel == evCar.wheel;
    }

}
