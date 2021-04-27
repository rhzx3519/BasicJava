package com.example.maven.enumerate;

import com.example.maven.inter.Fruit;
import lombok.extern.slf4j.Slf4j;

/**
 * @auther louzh
 * @create 2020/04/28
 */

enum Bike {
    EASY(0, "EXT001"), HARD(1, "EXT1025");

    private int pos;
    private String prefix;

    Bike(int pos, String prefix) {
        this.pos = pos;
        this.prefix = prefix;
    }

    int getPos() {
        return pos;
    }

    String getPrefix() {
        return prefix;
    }
}

enum Car {
    lamborghini(900, "Italy"),tata(2, "India"),audi(50, "Genmen"),
    fiat(15, "Italy"), honda(12, "Japan");

    private int price;
    private String country;

    Car(int p, String country) {
        price = p;
        country = country;
    }
    int getPrice() {
        return price;
    }

    String getCountry() {
        return country;
    }
}

enum Day {
    MONDAY(1, "星期一", "星期一各种不在状态"),
    TUESDAY(2, "星期二", "星期二依旧犯困"),
    WEDNESDAY(3, "星期三", "星期三感觉半周终于过去了"),
    THURSDAY(4, "星期四", "星期四期待这星期五"),
    FRIDAY(5, "星期五", "星期五感觉还不错"),
    SATURDAY(6, "星期六", "星期六感觉非常好"),
    SUNDAY(7, "星期日", "星期日感觉周末还没过够。。。");

    Day(int index, String name, String value) {
        this.index = index;
        this.name = name;
        this.value = value;
    }

    private int index;
    private String name;
    private String value;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

enum ClearingBroker {
    IB,
    Apex,
    AMTD,
    ODD,
    HH,
    NZX,
    OJ,
    GHF,
    XYA,
    CGS_CIMB,
    TBNZ;
}

@Slf4j
public class EnumTest {
    Day day;

    public EnumTest(Day day) {
        this.day = day;
    }

    public void tellItLikeItIs() {
        switch (day) {
            case MONDAY:
                System.out.println(day.getName()+day.getValue());
                break;

            case FRIDAY:
                System.out.println(day.getName()+day.getValue());
                break;

            case SATURDAY: case SUNDAY:
                System.out.println(day.getName()+day.getValue());
                break;

            default:
                System.out.println(day.getName()+day.getValue());
                break;
        }
    }

    public static void main(String[] args) {
        System.out.println("所有汽车的价格：");
        for (Car c : Car.values())
            System.out.println(c + " 需要 "
                    + c.getPrice() + " 千美元。");

        for (Bike file : Bike.values()) {
            System.out.println(file + " " + file.getPos() + file.getPrefix());
        }

        log.info("{}", ClearingBroker.Apex.name());
    }
}

