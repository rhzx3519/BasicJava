package com.example.maven.annotation;

/**
 * @auther louzh
 * @create 2020/04/16
 */
@FruitName(value = "Apple")
public class Apple {

    public static void main(String[] args) {
        Apple apple = new Apple();
        
        System.out.println(apple);

    }
}
