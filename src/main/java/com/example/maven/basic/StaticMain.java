package com.example.maven.basic;

/**
 * @author ZhengHao Lou
 * @date 2020/9/3
 */
public class StaticMain {
    static class Compiler {

    }
    static class CompilerA extends Compiler{

    }

    static class CompilerB extends Compiler{

    }

    static class CompilerFactory {
        private static final CompilerA compilerA = new CompilerA();
        private static final CompilerB compilerB = new CompilerB();
        private static final int CURRENT_TYPE = 1;

        private static Compiler getCompiler(int type) {
            if (type==1) {
                return compilerA;
            } else {
                return compilerB;
            }

        }

        public static Compiler getCompiler() {
           return getCompiler(CURRENT_TYPE);
        }
    }

    public static void main(String[] args) {
        CompilerFactory.getCompiler();
    }
}
