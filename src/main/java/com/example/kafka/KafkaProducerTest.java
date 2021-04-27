package com.example.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.locks.LockSupport;

/**
 * @author ZhengHao Lou
 * @date 2021/02/18
 */
public class KafkaProducerTest implements Runnable {
    private final KafkaProducer<String, String> producer;
    private final String topic;
    public KafkaProducerTest(String topicName) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("producer.type", "sync");
        props.put("request.required.acks", 0);
        props.put("batch.size", 16384);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // Define whether the timestamp in the message is message create time or log append time. The value should be either `CreateTime` or `LogAppendTime`
        // http://kafka.apache.org/documentation.html#producerconfigs
        props.put("log.message.timestamp.type", "LogAppendTime");
        this.producer = new KafkaProducer<>(props);
        this.topic = topicName;
    }

    @Override
    public void run() {
        int messageNo = 1;
        try {
            for(;;) {
                String messageStr="你好，这是第"+messageNo+"条数据";
                Future<RecordMetadata> future = producer.send(new ProducerRecord<>(topic, "Message", messageStr));
                RecordMetadata recordMetadata = future.get();
                System.out.println("发送的信息:" + messageStr);
                LockSupport.parkNanos(Duration.ofSeconds(1).toNanos());
                messageNo++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

    public static void main(String args[]) {
        KafkaProducerTest test = new KafkaProducerTest("KAFKA_TEST");
        Thread thread = new Thread(test);
        thread.start();
    }
}
