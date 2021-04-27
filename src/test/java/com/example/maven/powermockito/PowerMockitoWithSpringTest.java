package com.example.maven.powermockito;

import com.example.maven.DemoTestApplication;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
@PrepareForTest({SpringService.class})
public class PowerMockitoWithSpringTest {

    @SpyBean
    private SpringService service;

    @Test
    public void testMockStaticMethod() {
        PowerMockito.mockStatic(SpringService.class);
        PowerMockito.when(service.staticMethod()).thenReturn("456");
        String str = service.staticMethod();
        Assert.assertEquals("456", str);
    }

    @Test
    public void testMockPrivateMethod() throws Exception {
        service = PowerMockito.spy(service);

        PowerMockito.when(service.callPrivateMethod()).thenCallRealMethod();
        PowerMockito.doReturn("456").when(service, "privateMethod");
        String str = service.callPrivateMethod();
        Assert.assertEquals("456", str);

        PowerMockito.when(service.callHello(Mockito.anyString())).thenCallRealMethod();
        String helloResult = service.callHello("Jim");
        Assert.assertEquals("Hello, Jim", helloResult);
    }

    @Test
    public void testMockFinalMethod() throws Exception {
        service = PowerMockito.spy(service);

        PowerMockito.when(service.callFinalMethod()).thenCallRealMethod();
        PowerMockito.doReturn("456").when(service, "finalMethod");
        String str = service.callFinalMethod();
        Assert.assertEquals("456", str);
    }
}
