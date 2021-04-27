package com.example.maven.redis;

import com.example.maven.BaseTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ZhengHao Lou
 * @date 2020/05/01
 */
public class RedisTest extends BaseTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testRedisConnection() {
        redisUtil.set("key1", "value1", 10);
        String val = (String) redisUtil.get("key1");
        Assert.assertEquals("value1", val);
    }

}
