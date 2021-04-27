package com.example.maven.service;

import com.example.maven.exception.LogicErrorException;

/**
 * @author ZhengHao Lou
 * @date 2020/05/21
 */
public interface LogicService {

    String runLogic() throws LogicErrorException;

    String foo();

}
