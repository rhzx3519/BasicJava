package com.example.maven.container;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author ZhengHao Lou
 * @date 2020/10/31
 */
@Slf4j
public class MapTest {

    @Test
    public void test1() {
        Map<Long, String> map = Maps.newConcurrentMap();
        map.put(1L, "AAPL");
        map.put(2L, "TSLA");
        Iterator<String> iterator = map.values().iterator();
        while (iterator.hasNext()) {
            String val = iterator.next();
            if (val == "AAPL") {
                iterator.remove();
            }
        }
        log.info("{}", map);
    }

}
