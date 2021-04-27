package com.example.maven.third;

import com.example.maven.data.User;
import com.example.maven.springboot.BeanUtil;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

/**
 * @author ZhengHao Lou
 * @date 2020/11/30
 */
@Slf4j
public class BeanUtilTest {
    public static void main(String[] args)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        User user = new User();
        User cpUser = new User();
        cpUser.setTotalQty(BigDecimal.ONE);
        BeanUtils.copyProperty(cpUser,"totalQty", null);
        //BeanUtils.setProperty(cpUser,"totalQty", null);
        //FooBeanUtils.copyPropertiesIgnoreNull(cpUser, user);
        log.info("{}", cpUser);
    }
}
