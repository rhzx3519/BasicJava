package com.example.maven.stream;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * @author ZhengHao Lou
 * @date 2020/05/18
 */
@Slf4j
public class StreamTest {

    @Test
    public void testUpdateMapInStream() {
        Map<String, Long> map = Maps.newHashMap();
        List<String> lists = Lists.newArrayList("1", "2", "3");
        lists.stream().forEach(str -> {
            map.put(str, 1L);
        });
        log.info("{}", map.toString());
        Assert.assertEquals(3, map.size());
    }

}
