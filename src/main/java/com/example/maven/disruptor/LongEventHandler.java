package com.example.maven.disruptor;

/**
 * @author ZhengHao Lou
 * @date 2020/7/15
 */
import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent>
{
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch)
    {
        System.out.println("Event: " + event);
    }
}