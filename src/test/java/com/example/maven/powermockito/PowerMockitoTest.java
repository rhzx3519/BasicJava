package com.example.maven.powermockito;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author ZhengHao Lou
 * @date 2020/06/19
 */

class StaticClz {
    public static String staticMethod() {
        return "123";
    }
}

class PrivateClz {

    public String callPrivateMethod() {
       return this.privateMethod();
    }

    private String privateMethod() {
        return "123";
    }
}

class FinalClz {
    public String callFinalMethod() {
        return this.finalMethod();
    }

    private final String finalMethod() {
        return "123";
    }
}


@Slf4j
@RunWith(PowerMockRunner.class)
@PrepareForTest({StaticClz.class, PrivateClz.class, FinalClz.class})
public class PowerMockitoTest {

    @Test
    public void testMockStaticMethod() {
        PowerMockito.mockStatic(StaticClz.class);
        PowerMockito.when(StaticClz.staticMethod()).thenReturn("456");
        String str = StaticClz.staticMethod();
        Assert.assertEquals("456", str);
    }

    @Test
    public void testMockPrivateMethod() throws Exception {
        PrivateClz privateClz = PowerMockito.mock(PrivateClz.class);
        PowerMockito.when(privateClz.callPrivateMethod()).thenCallRealMethod();
        PowerMockito.when(privateClz, "privateMethod").thenReturn("456");
        String str = privateClz.callPrivateMethod();
        Assert.assertEquals("456", str);
    }

    @Test
    public void testMockFinalMethod() throws Exception {
        FinalClz finalClz = PowerMockito.mock(FinalClz.class);
        PowerMockito.when(finalClz.callFinalMethod()).thenCallRealMethod();
        PowerMockito.when(finalClz, "finalMethod").thenReturn("456");
        String str = finalClz.callFinalMethod();
        Assert.assertEquals("456", str);
    }

}








