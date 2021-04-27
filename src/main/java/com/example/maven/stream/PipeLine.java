package com.example.maven.stream;

/**
 * @author ZhengHao Lou
 * @date 2020/05/07
 */
public class PipeLine {

    public PipeLine() {}

    public PipeLine filer() {
       return new StatelessOp() {

       };

    }

    abstract static class StatelessOp extends PipeLine{
        StatelessOp() {}
    }

}
