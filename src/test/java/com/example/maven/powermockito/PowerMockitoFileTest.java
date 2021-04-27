package com.example.maven.powermockito;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author ZhengHao Lou
 * @date 2020/7/12
 */



@Slf4j
@RunWith(PowerMockRunner.class)
public class PowerMockitoFileTest {

    static class Fetcher {
        /**
         * 解析文本，返回解析后的数据
         * @return
         */
        public String parse() {
            return readFromFile();
        }

        /**
         * 从文件中读取文本返回字符串
         * @return String
         */
        private String readFromFile() {
            // FtpFile file = FtpUtils.get()
            return "Text";
        }
    }

    /**
     * 测试：mock Fetcher的readFromFile函数
     */
    @Test
    public void test() throws Exception {
        Fetcher fetcher = new Fetcher();
        fetcher = PowerMockito.spy(fetcher);
        PowerMockito.when(fetcher.parse()).thenCallRealMethod();
        PowerMockito.doReturn("Number").when(fetcher, "readFromFile");
        String str = fetcher.parse();
        log.info(str);
        Assert.assertEquals("Number", str);
    }
}

