package com.example.maven.guava;

import com.google.common.base.Joiner;
import java.util.Arrays;

/**
 * @author ZhengHao Lou
 * @date 2020/11/30
 */
public class JoinerTest {
    public static class RejectCode {
        /**
         * 对应alpha-service, resources/messages.properties里面的文案key
         */
        private final String code;

        /**
         * 对应alpha-service, resources/messages.properties里面的文案key
         */
        private final String[] data;

        public RejectCode(String code, String... data) {
            this.code = code;
            this.data = data;
        }

        public String getCode() {
            return code;
        }

        public String[] getData() {
            return data;
        }

        public String getDataString() {
            if (data == null) {
                return null;
            }
            return Joiner.on("").join(data);
        }

        @Override
        public String toString() {
            return "RejectCode{" +
                    "code='" + code + '\'' +
                    ", data=" + Arrays.toString(data) +
                    '}';
        }
    }

    public static void main(String[] args) {
        //String[] data = null;
        //Joiner.on(",").join(data);
        RejectCode rejectCode = new RejectCode("code", null, "");
        rejectCode.getDataString();
    }

}
