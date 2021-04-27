package com.example.maven.basic.collections;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author ZhengHao Lou
 * @date 2020/9/13
 */
@Slf4j
public class LinkedListMain {
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = Lists.newLinkedList();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        Iterator iter = linkedList.iterator();
        while (iter.hasNext()) {
            Integer val = (Integer) iter.next();
            if (val == 2) {
                iter.remove();
            }
        }

        log.info("{}", linkedList);
    }
}
