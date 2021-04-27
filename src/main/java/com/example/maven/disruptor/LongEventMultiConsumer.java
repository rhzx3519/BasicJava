package com.example.maven.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author ZhengHao Lou
 * @date 2020/7/21
 */
@Slf4j
public class LongEventMultiConsumer {

    static class MyEventHandler {
        private int count = 0;

        public void process(long seq, CommandEvent commandEvent) {
            count += 1;
            System.out.println("Thread: " + Thread.currentThread().getId() + ", count: " + count);
        }

        @SuppressWarnings(value = {"unchecked"})
        private static EventHandler<CommandEvent>[] newEventHandlersArray(int size) {
            return new EventHandler[size];
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<CommandEvent> disruptor = new Disruptor<>(CommandEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<CommandEvent> ringBuffer = disruptor.getRingBuffer();
        List<MyEventHandler> myEventHandlerList = IntStream.range(0, 2).boxed()
                .map(i -> new MyEventHandler()).collect(Collectors.toList());

        final EventHandler<CommandEvent>[] eventHandlers = myEventHandlerList.stream()
                .map(myEventHandler -> (EventHandler<CommandEvent>) (cmd, seq, eob) -> myEventHandler.process(seq, cmd))
                .toArray(MyEventHandler::newEventHandlersArray);

        disruptor.handleEventsWith(eventHandlers);
        // Connect the handler
        EventHandlerGroup<CommandEvent> eventHandlerGroup = disruptor.handleEventsWith((event, sequence, endOfBatch) -> {
                    System.out.println("Event1: " + event + ", Thread: " + Thread.currentThread().getName());
                }
        );

        // Start the Disruptor, starts all threads running
        disruptor.start();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++)
        {
            bb.putLong(0, l);
            ringBuffer.publishEvent((event, sequence, buffer) -> {}, bb);
            System.out.println("publish Event Thread: " + Thread.currentThread().getId());
            Thread.sleep(1000);
        }
    }
}
