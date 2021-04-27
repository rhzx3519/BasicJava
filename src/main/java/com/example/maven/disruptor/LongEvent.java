package com.example.maven.disruptor;

import lombok.Getter;
import lombok.Setter;

/**
 * {@link Long#valueOf(String)}
 * @author ZhengHao Lou
 * @date 2020/7/15
 */
public final class LongEvent
{
    @Setter
    @Getter
    public long value;
}