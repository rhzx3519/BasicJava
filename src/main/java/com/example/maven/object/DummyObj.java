package com.example.maven.object;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2020/8/18
 */
@Slf4j
@Data
public class DummyObj {
    int id;

    public static void main(String[] args) {
        DummyObj dummyObj = new DummyObj();
    }
}
