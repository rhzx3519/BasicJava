package com.example.maven.bitset;

import java.util.BitSet;

/**
 * @author ZhengHao Lou
 * @date 2020/7/27
 */
public class Base {
    public static void main(String[] args) {
        BitSet bitSet = new BitSet(64);
        bitSet.set(0);
        System.out.println(bitSet.cardinality());
    }
}
