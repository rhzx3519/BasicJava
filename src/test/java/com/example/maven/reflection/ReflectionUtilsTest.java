package com.example.maven.reflection;

import java.lang.reflect.InvocationTargetException;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.when;

/**
 * @author ZhengHao Lou
 * @date 2020/05/29
 */
public class ReflectionUtilsTest{

    private String name;

    private void setName(String name) {
        this.name = name;
    }

    private String getName() {
        return this.name;
    }

    @Test
    public void test() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ReflectionUtilsTest reflectionUtilsTest = new ReflectionUtilsTest();
        //访问私有属性
        System.out.println("name = " + ReflectionUtils.getPrivateField(reflectionUtilsTest, "name"));
        //设置私有属性
        ReflectionUtils.setPrivateField(reflectionUtilsTest, "name", "张三");
        System.out.println("name = " + ReflectionUtils.getPrivateField(reflectionUtilsTest, "name"));
        //调用私有方法
        ReflectionUtils.invokePrivateMethod(reflectionUtilsTest, "setName", new Class[]{String.class}, "李四");
        System.out.println("name = " + ReflectionUtils.getPrivateField(reflectionUtilsTest, "name"));
    }

}
