package com.example.maven.powermockito;

import com.example.maven.DemoTestApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ZhengHao Lou
 * @date 2020/06/19
 */


@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DemoTestApplication.class})
@PrepareForTest({TextFetcher.class})
public class MockitoTest {

    @SpyBean
    private TextFetcher fetcher;

    /**
     * 测试：mock TextFetcher的私有方法：download
     */
    @Test
    public void testParse() throws Exception {
        fetcher = PowerMockito.spy(fetcher);

        PowerMockito.when(fetcher.parse()).thenCallRealMethod();
        PowerMockito.doReturn("Number").when(fetcher, "download");
        String str = fetcher.parse();
        Assert.assertEquals("Number", str);
    }
}
