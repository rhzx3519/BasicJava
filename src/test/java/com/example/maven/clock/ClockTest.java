package com.example.maven.clock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

/**
 * @author ZhengHao Lou
 * @date 2020/7/8
 */
@Slf4j
public class ClockTest {

    @Autowired
    private Clock clock;

    @Test
    public void testClock() throws InterruptedException {
        //时钟提供给我们用于访问某个特定 时区的 瞬时时间、日期 和 时间的。
        Clock c1 = Clock.systemUTC(); //系统默认UTC时钟（当前瞬时时间 System.currentTimeMillis()）
        System.out.println(c1.millis()); //每次调用将返回当前瞬时时间（UTC）
        Clock c2 = Clock.systemDefaultZone(); //系统默认时区时钟（当前瞬时时间）
        Clock c31 = Clock.system(ZoneId.of("Europe/Paris")); //巴黎时区
        System.out.println(c31.millis()); //每次调用将返回当前瞬时时间（UTC）
        Clock c32 = Clock.system(ZoneId.of("Asia/Shanghai"));//上海时区
        System.out.println(c32.millis());//每次调用将返回当前瞬时时间（UTC）
        Clock c4 = Clock.fixed(Instant.now(), ZoneId.of("Asia/Shanghai"));//固定上海时区时钟
        System.out.println(c4.millis());
        Thread.sleep(1000);
        System.out.println(c4.millis()); //不变 即时钟时钟在那一个点不动
        Clock c5 = Clock.offset(c1, Duration.ofMillis(100)); //相对于系统默认时钟100ms的时钟
        System.out.println(c5.millis());

        System.out.println(c1.millis());
        Thread.sleep(1000);
        System.out.println(c1.millis());
    }

    @Test
    public void testNormalClock() throws InterruptedException {
        Clock c1 = Clock.systemUTC(); //系统默认UTC时钟（当前瞬时时间 System.currentTimeMillis()）
        System.out.println(c1.millis());
        Thread.sleep(1000);
        System.out.println(c1.millis());
    }

    @Test
    public void testFixedClock() throws InterruptedException {
        Clock.fixed(Instant.now(), ZoneId.of("Asia/Shanghai"));//固定上海时区时钟
        Clock c1 = Clock.systemUTC(); //系统默认UTC时钟（当前瞬时时间 System.currentTimeMillis()）
        System.out.println(c1.millis());
        Thread.sleep(1000);
        c1 = Clock.systemUTC();
        System.out.println(c1.millis());
    }
}
