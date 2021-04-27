package com.example.maven.disruptor;

/**
 * @author ZhengHao Lou
 * @date 2020/7/15
 */
import com.lmax.disruptor.EventFactory;

public class LongEventFactory implements EventFactory<LongEvent>
{
    public LongEvent newInstance()
    {
        return new LongEvent();
    }
}