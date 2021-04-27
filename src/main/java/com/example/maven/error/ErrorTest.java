package com.example.maven.error;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import jodd.exception.ExceptionUtil;
import jodd.util.ArraysUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author ZhengHao Lou
 * @date 2020/11/27
 */
@Slf4j
public class ErrorTest {

    @Test
    public void test1() {
        AssertionError error1 = new AssertionError("1st error.");
        AssertionError error2 = new AssertionError("2nd error");
        List<AssertionError> errors = Lists.newArrayList(error1, error2);
        AssertionError lastError = new AssertionError("3rd error");
        AssertionError tmp = lastError;
        for (int i = errors.size() - 1; i >= 0; i--) {
            tmp.initCause(errors.get(i));
            tmp = errors.get(i) ;
        }

        log.info("{}", lastError);
    }

    private static class AssertionErrorCollection extends Error{

        private static final long serialVersionUID = 7119235592579165806L;

        private List<Throwable> causes;

        public AssertionErrorCollection(String message) {
            super(message);
            this.causes = Lists.newArrayList();
        }

        public AssertionErrorCollection(String message, Collection<? extends Throwable> causes) {
            super(message);
            this.causes = Lists.newArrayList(causes);
        }


        public void addCause(Throwable cause) {
            this.causes.add(cause);
        }

        @Override
        public String toString() {
            return super.toString() + System.lineSeparator() + Joiner.on(System.lineSeparator()).join(this.causes.stream().map(Throwable::toString).collect(Collectors.toList()));
        }
    }

    public static void main(String[] args) throws Throwable {
        List<AssertionError> mockErrors = Lists.newArrayList();

        mockErrors.add(new AssertionError("1st error."));


        mockErrors.add(new AssertionError("2nd error"));

        //AssertionErrorCollection assertionErrorCollection = new AssertionErrorCollection("test collection", mockErrors);
        //throw assertionErrorCollection;

        for (Throwable error : mockErrors) {
            //log.info("{}", error);
            //log.info("{}", error.getMessage());
            //log.info("{}", error.getStackTrace());
        }
        //
        //List<StackTraceElement[]> stackTraceElements = mockErrors.stream().map(Throwable::getStackTrace).collect(Collectors.toList());
        //int ln = 0;
        //for (StackTraceElement[] ste : stackTraceElements) {
        //    ln += ste.length;
        //}
        //StackTraceElement[] newStackTraceElements = new StackTraceElement[ln];
        //int idx = 0;
        //for (StackTraceElement[] ste : stackTraceElements) {
        //    System.arraycopy(ste, 0, newStackTraceElements, idx, ste.length);
        //    idx += ste.length;
        //}
        //AssertionError assertionError = new AssertionError("Summary");
        //assertionError.setStackTrace(newStackTraceElements);
        //throw assertionError;
    }
}
