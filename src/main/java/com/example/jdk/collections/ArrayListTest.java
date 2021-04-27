package com.example.jdk.collections;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author ZhengHao Lou
 * @date 2021/01/12
 */
@Slf4j
public class ArrayListTest {

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

    private void foo(Enum... enums) {
        log.info("{}", enums.length);
        for (int i = 0; i < enums.length; i++) {
            Enum e = enums[i];
            log.info(e.getClass().getSimpleName());
            log.info(e.name());
        }

    }

    private void bar(String... strings) {
        for (int i = 0; i < strings.length; i++) {
            String str = strings[i];
        }
    }

    @Test
    public void test1() {
        List<List<Integer>> array = new ArrayList<>(2);
        array.get(0).add(0);
    }

    @Test
    public <T, E> void test2() {
        foo();
    }

    @Test
    public void test3() {
        List<Integer> list = Lists.newArrayList(0, 1);
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            log.info("{}", iterator.next());
        }

        List<Integer> linkedList = Lists.newLinkedList();
        linkedList.add(0);
        linkedList.add(1);
        linkedList.subList(0, linkedList.size() - 1);
        Spliterator<Integer> spliterator = linkedList.spliterator();
    }

}
