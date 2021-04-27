package com.example.maven.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author ZhengHao Lou
 * @date 2020/10/22
 */
@Slf4j
public class RawQuote {
    private String symbol;
    private long timestamp;
    private double latestPrice;
    private String latestTime;
    private long volume;
    private long open;
    private String marketStatus;
    private double askPrice;
    private long askSize;
    private double bidPrice;
    private long bidSize;
    private int offset;
    /**
     * mq 消息中的时间戳, 一般是producer发送消息的时间
     */
    private long messageTimestamp;
    /**
     * algo 接收到该行情消息的时间
     */
    private long receiveTimestamp;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class QuoteMessage {
        private Long timestamp;
        private RawQuote data;
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper;
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String message = "{\"data\":{\"latestPrice\":1.6777,\"askPrice\":1.68,\"symbol\":\"HTZ\",\"amount\":8.319457586960049E7,\"bidSize\":433,\"type\":\"quote\",\"bidPrice\":1.67,\"volume\":50412744,\"high\":1.79,\"preClose\":1.84,\"low\":1.55,\"marketStatus\":\"交易中\",\"latestTime\":\"10-21 15:08:50 EDT\",\"mi\":{\"p\":1.6777,\"a\":1.646929,\"t\":1603307280000,\"v\":75433,\"h\":1.68,\"l\":1.6701},\"open\":1.6,\"askSize\":466,\"timestamp\":1603307330092},\"timestamp\":1603307330428}";
        QuoteMessage msg = objectMapper.readValue(message, QuoteMessage.class);
        log.info("{}", msg);
    }
}
