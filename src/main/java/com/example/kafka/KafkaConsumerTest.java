package com.example.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.locks.LockSupport;

/**
 * @author ZhengHao Lou
 * @date 2021/02/18
 */
@Slf4j
public class KafkaConsumerTest implements Runnable {
    private final KafkaConsumer<String, String> consumer;
    private ConsumerRecords<String, String> msgList;
    private final String topic;
    private static final String GROUPID = "groupA";

    public KafkaConsumerTest(String topicName) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", GROUPID);
        props.put("enable.auto.commit", "true");
        props.put("max.poll.records", "100000");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer = new KafkaConsumer<String, String>(props);
        this.topic = topicName;
        this.consumer.subscribe(Arrays.asList(topic));
    }

    @Override
    public void run() {
//        try {
//            consumer.poll(Duration.ofMillis(1000));
//            Set<TopicPartition> partitionSet = consumer.assignment();
//
//            Map<TopicPartition, Long> timestampsToSearch = Maps.newHashMap();
//            for (TopicPartition tp : partitionSet) {
//                timestampsToSearch.put(tp, System.currentTimeMillis() - Duration.ofSeconds(60).toMillis());
//            }
//
//            Map<TopicPartition, OffsetAndTimestamp> offsetAndTimestampMap = consumer.offsetsForTimes(timestampsToSearch);
//            for (TopicPartition tp : offsetAndTimestampMap.keySet()) {
//                OffsetAndTimestamp offsetAndTimestamp = offsetAndTimestampMap.get(tp);
//                if (offsetAndTimestamp == null) {
//                    continue;
//                }
//                log.info("Consumer start from offset: {}", offsetAndTimestamp.offset());
//                consumer.seek(tp, offsetAndTimestamp.offset());
//                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
//                System.out.println("本次拉取的消息数量:" + consumerRecords.count());
//                System.out.println("消息集合是否为空:" + consumerRecords.isEmpty());
//                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
//                    System.out.println("消费到的消息key:" + consumerRecord.key() + ",value:" + consumerRecord.value() + ",offset:" + consumerRecord.offset());
//                }
//            }
//        } finally {
//            consumer.close();
//        }

        //
        //
        int messageNo = 1;
        System.out.println("---------开始消费---------");
        try {
            for (;;) {
                msgList = consumer.poll(1000);
                if(null!=msgList&&msgList.count()>0){
                    for (ConsumerRecord<String, String> record : msgList) {
                        log.info("{}, {}", Thread.currentThread(), messageNo+"=======receive: key = " + record.key() + ", value = " + record.value()+" offset==="+record.offset());
                        messageNo++;
                    }
                }else{
                    LockSupport.parkNanos(Duration.ofMillis(1000).toNanos());
                }
            }
        } finally {
            consumer.close();
        }
    }
    public static void main(String args[]) {
        KafkaConsumerTest test1 = new KafkaConsumerTest("KAFKA_TEST");
        Thread thread1 = new Thread(test1);
        thread1.start();

        KafkaConsumerTest test2 = new KafkaConsumerTest("KAFKA_TEST");
        Thread thread2 = new Thread(test2);
        thread2.start();
    }
}
