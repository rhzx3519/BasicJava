package com.example.maven.guava;

/**
 * @author ZhengHao Lou
 * @date 2020/06/10
 */
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * EventBus,事件总线，guava基于观察者模式的优雅实现。对于事件监听和发布订阅模式，
 * 使用guava eventbus可以省去开发人员很多事情,不用在去定义那些复杂的类或接口来实现事件发布与订阅。
 * 在guava eventbus中，开发人员只需要在订阅方法上添加上@Subscribe注解就可以了,
 * 这样一来就省去了大量共用的编码工作。guava eventbus提供了同步或者异步消息发布的实现，
 * 用户可以根据需要编写相关的代码。使用异步消息时需要通过一个Executor来辅助。
 * <p>2018年1月30日 11点30分
 * liyang
 * <p>
 * EventBus中的事件可以了是任意类型的，事件分发的时候只需要根据订阅参数类型来分发消息，
 * 如果编码中，多个订阅事件类型上存在类型继承的关系，则当前的事件会分发到多个不同的订阅者上，
 * 这一点大家在使用的时候可能要仔细处理，在不需要重复处理的消息上就要做好细节处理了。
 * 另外，guava eventbus中默认订阅方法为线程不安全的，在异步调度时会自动将其包装成线程安全的方法。
 * 对于一般线程安全的实现上，可以通过@AllowConcurrentEvents注解来标识。如果发布了某些未被订阅的事件，
 * 可以通过DeadEvent获取,测试代码如下:
 */
public class EventBusTest {
    private static class EventA {
        public String toString() {
            return "A 类型事件";
        }
    }

    private static class EventB extends EventA {
        public String toString() {
            return "B 类型事件";
        }
    }

    private static class EventC {
        public String toString() {
            return "C 类型事件";
        }
    }

    private static class EventD {
        public String toString() {
            return "D 类型事件";
        }
    }

    private static class EventX {
        public String toString() {
            return "X 类型事件";
        }
    }

    private static class EventListener {
        @Subscribe
        public void onEvent(EventA e) {
            System.out.println(Thread.currentThread().getName() + "我订阅的是 A事件,接收到:" + e);
        }

        @Subscribe
        public void onEvent(EventB e) {
            System.out.println("我订阅的是B事件,接收到:" + e);
        }

        @Subscribe
        @AllowConcurrentEvents
        public void onEvent(EventC e) throws InterruptedException {
            String name = Thread.currentThread().getName();
            System.out.format("%s sleep 一会儿%n", name);
            Thread.sleep(1000);
            System.out.println(name + "订阅的是C事件,接收到:" + e);
            System.out.format("%s sleep 完成%n", name);
        }

        // @Subscribe
        //没有该注解表示未订阅
        public void onEvent(EventD e) throws InterruptedException {
            String name = Thread.currentThread().getName();
            System.out.format("%s sleep 一会儿%n", name);
            Thread.sleep(1000);
            System.out.println(name + "订阅的是D事件,接收到:" + e);
            System.out.format("%s sleep 完成%n", name);
        }

        @Subscribe
        public void onEvent(DeadEvent de) {
            System.out.println(Thread.currentThread().getName() + "发布了错误的事件:" + de.getEvent());
        }
    }

    @Test
    public void singleThreadTest() {
        EventBus eb = new EventBus();
        eb.register(new EventListener());
        eb.post(new EventA());
        eb.post(new EventX());
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        EventBus eb = new AsyncEventBus(threadPool);
        eb.register(new EventListener());
        eb.post(new EventC());
        eb.post(new EventA());

        //未订阅事件
        eb.post(new EventD());
        threadPool.shutdown();
    }
}