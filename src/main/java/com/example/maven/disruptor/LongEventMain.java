package com.example.maven.disruptor;

/**
 * @author ZhengHao Lou
 * @date 2020/7/15
 */
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import lombok.extern.slf4j.Slf4j;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.slf4j.MDC;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @
 */
@Slf4j
public class LongEventMain
{

    public static void main(String[] args) throws Exception
    {
        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(() -> new LongEvent(), bufferSize, new ThreadFactory() {

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

        // Connect the handler
        EventHandlerGroup<LongEvent> eventHandlerGroup = disruptor.handleEventsWith((event, sequence, endOfBatch) -> {
                    MDC.put("Event1 threadId", Thread.currentThread().getId() + "");
                        System.out.println("Event1: " + event + ", Thread: " + Thread.currentThread().getName());
                    }
                );

        eventHandlerGroup.handleEventsWith((event, sequence, endOfBatch) -> {
            System.out.println("Event2: " + event +
                    ", Thread: " + Thread.currentThread().getName());
            final String val = MDC.get("Event1 threadId");
            System.out.println(val);
        });

        // Start the Disruptor, starts all threads running
        disruptor.start();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++)
        {
            bb.putLong(0, l);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.setValue(buffer.getLong(0)), bb);
            System.out.println("publish Event Thread: " + Thread.currentThread().getId());
            Thread.sleep(1000);
        }
    }
}