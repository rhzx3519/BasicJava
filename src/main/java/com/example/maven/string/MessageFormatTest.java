package com.example.maven.string;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.helpers.MessageFormatter;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class MessageFormatTest {
    @Data
    private static class User {
        @Setter
        private String name;
    }

    public static void main(String[] args) {
        String msg = MessageFormat.format("{0} 盘中振幅达 {1}%, {2}, {3}", "2", "2",
                true ? "LMM: " + 50 + "%": "不可融资",
                true ? "SMM: " + 50 + "%": "不可融券");
        log.info(msg);
        User user = new User();
        msg = MessageFormat.format("{0}", user);
        log.error(msg);

        String str = MessageFormatter.arrayFormat("remote: {}", new Object[]{"jim"}).getMessage();
        log.info(str);
        log.info(MessageFormatTest.class.getSimpleName());
        List<Integer> sortedList = Lists.newArrayList();
        sortedList.add(0, 1);
        Arrays.binarySearch(sortedList.toArray(), 1);
    }

    @Test
    public void test1() {
        Object[] params = new Object[]{5271150308346246954L};
        String msg = MessageFormatter.arrayFormat("No AlgoOrder {}", params).getMessage();
        log.info(msg);
    }

    @Test
    public void test2() {
        MessageFormatter.arrayFormat("Check send oes request failled, formula: "
                        + "qty + filledQty <= totalQty. opType: {}, algoOrder: {}, oesRequest: {}",
                new Object[]{"a", "b", "c"});
    }
}
