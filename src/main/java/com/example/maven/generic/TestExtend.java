package com.example.maven.generic;

import com.google.common.collect.Lists;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2020/12/15
 */
@Slf4j
public class TestExtend {

    class Food {}
    abstract class Fruit extends Food {
        public abstract String name();
    }
    class Apple extends Fruit {

        @Override
        public String name() {
            return "Apple";
        }
    }
    class Banana extends Fruit{

        @Override
        public String name() {
            return "Banana";
        }
    }

    public void testExtends(List<? extends Fruit> list){

        //报错,extends为上界通配符,只能取值,不能放.
        //因为Fruit的子类不只有Apple还有Banana,这里不能确定具体的泛型到底是Apple还是Banana，所以放入任何一种类型都会报错
        //list.add(new Apple());

        //可以正常获取
        Fruit fruit = list.get(0);
    }

    public void testSuper(List<? super Fruit> list){

        //super为下界通配符，可以存放元素，但是也只能存放当前类或者子类的实例，以当前的例子来讲，
        //无法确定Fruit的父类是否只有Food一个(Object是超级父类)
        //因此放入Food的实例编译不通过
        list.add(new Apple());
        //        list.add(new Food());
        list.add(new Banana());

        for (Object fruit : list) {
            Fruit f = (Fruit)fruit;
            log.info(f.name());
        }

        log.info("{}", list);
    }

    public static void main(String[] args) {
        TestExtend te = new TestExtend();
        List<? super Fruit> list = Lists.newLinkedList();
        te.testSuper(list);
    }

}
