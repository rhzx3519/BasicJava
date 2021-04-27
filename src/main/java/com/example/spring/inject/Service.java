package com.example.spring.inject;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ZhengHao Lou
 * @date 2021/01/07
 */
@Component
public class Service {

    @Autowired
    private List<Base> baseList;

    public void say() {
        for (Base base : baseList) {
            base.hello();
        }
    }
}
