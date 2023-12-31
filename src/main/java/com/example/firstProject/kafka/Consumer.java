package com.example.firstProject.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Consumer {
    private static Map<Long, KafkaConsumer<String, String>> consumers = new HashMap<Long, KafkaConsumer<String, String>>();

    // 서버 확인
    public boolean checkServer(String ip, String port) {
        String bootstrapServerIp = ip;
        String bootstrapServerPort = port;

        // 카프카 정보
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", bootstrapServerIp + ":" + bootstrapServerPort);

        try {
            AdminClient client = AdminClient.create(kafkaParams);
            client.describeCluster().nodes().get(5, TimeUnit.SECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            return false;
        }
        return true;
    }

    // 구독
    public void subscribe(Long id, String ip, String port, String topic) {
        String bootstrapServerIp = ip;
        String bootstrapServerPort = port;
        String topicName = topic;
        String GROUP_ID = "consumer";

        // consumer properties
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerIp + ":" + bootstrapServerPort);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID + id.toString());
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        // consumer 객체 생성
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // 토픽 구독
        consumer.subscribe(Arrays.asList(topicName));

        // hashmap에 저장
        consumers.put(id, consumer);
    }

    // 조회
    public List getData(Long id) {
        // 데이터를 받아서 리스트로 반환
        ConsumerRecords<String, String> records = consumers.get(id).poll(Duration.ofSeconds(1));
        List list = new ArrayList<>();
        for (ConsumerRecord<String, String> record : records) {
            list.add(record.value());
        }
        return list;
    }

    // 중지
    public void stop(Long id) {
        // 구독 중지
        consumers.get(id).close();

        // hashmap에서 삭제
        consumers.remove(id);
    }
}
