package com.example.maven.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.Benchmark;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

/**
 * @author ZhengHao Lou
 * @date 2020/8/10
 */
@Slf4j
public class DisruptorBenchmark {

    // Specify the size of the ring buffer, must be power of 2.
    private static final int RING_BUFFER_SIZE = 1024*512;

    private static final long EVENT_NUM = 1_000_000L;

    /**
     * 一个ringbuffer对应2个handler
     */
    @Benchmark
    public void test1() throws InterruptedException {

        // Construct the Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(() -> new LongEvent(), RING_BUFFER_SIZE, new ThreadFactory() {

            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "" + threadNumber.getAndIncrement());
                thread.setUncaughtExceptionHandler((t, e) ->
                        log.error("Uncaught exception in thread " + t.getName() + ": " + e.getMessage())
                );
                return thread;
            }
        });

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        EventHandler<LongEvent>[] handlers = new EventHandler[]{(event, sequence, endOfBatch) -> {
            if ((sequence & 1) == 0) {
                return;
            }
            // do sth
//            log.info("Event0: {}, Thread: {}, Seq: {}", event, Thread.currentThread().getName(), sequence);

        }, (event, sequence, endOfBatch) -> {
            if ((sequence & 1) == 1) {
                return;
            }
            // do sth
//            log.info("Event1: {}, Thread: {}, Seq: {}", event, Thread.currentThread().getName(), sequence);
        }};
        disruptor.handleEventsWith(handlers);

        // Start the Disruptor, starts all threads running
        disruptor.start();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; l < EVENT_NUM; l++)
        {
            bb.putLong(0, l);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.setValue(buffer.getLong(0)), bb);
//            System.out.println("publish Event Thread: " + Thread.currentThread().getId());
//            sleep(1000);
        }
    }

    /**
     * 2个 1对1的ringbuffer-handler
     */
    @Benchmark
    public void test2() {

        Disruptor<LongEvent> d1 = getDisruptor();
        Disruptor<LongEvent> d2 = getDisruptor();
        d1.start();
        d2.start();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; l < EVENT_NUM; l++)
        {
            bb.putLong(0, l);
            if((l&1) == 0) {
                d1.getRingBuffer().publishEvent((event, sequence, buffer) -> event.setValue(buffer.getLong(0)), bb);
            } else {
                d2.getRingBuffer().publishEvent((event, sequence, buffer) -> event.setValue(buffer.getLong(0)), bb);
            }
        }
    }

    private  Disruptor<LongEvent> getDisruptor() {
        Disruptor<LongEvent> disruptor = new Disruptor<>(() -> new LongEvent(), RING_BUFFER_SIZE, new ThreadFactory() {

            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "" + threadNumber.getAndIncrement());
                thread.setUncaughtExceptionHandler((t, e) ->
                        log.error("Uncaught exception in thread " + t.getName() + ": " + e.getMessage())
                );
                return thread;
            }
        });

        // Get the ring buffer from the Disruptor to be used for publishing.
        disruptor.handleEventsWith((event, sequence, endOfBatch) ->{});

        return disruptor;
    }
}
