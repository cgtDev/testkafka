/*
 * @(#) ProducerTest.java 2016/04/28
 * 
 * Copyright 2016 snow.com, Inc. All rights reserved.
 */
package com.snow.kafka.producer;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author cgt
 * @version 2017/07/26
 */
@Component
public class ProducerTest {
    private static final Logger logger = LoggerFactory.getLogger(ProducerTest.class);
    public Properties createProperties() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        //发送确认信息的broker个数
        properties.put("acks", "all");
        properties.put("retries", 0); // 消息发送请求失败重试次数
        properties.put("batch.size", 80);
        properties.put("linger.ms", 1); // 消息逗留在缓冲区的时间，等待更多的消息进入缓冲区一起发送，减少请求发送次数
        properties.put("buffer.memory", 33554432); // 内存缓冲区的总量
        // 如果发送到不同分区，并且不想采用默认的Utils.abs(key.hashCode) % numPartitions分区方式，则需要自己自定义分区逻辑
        properties.put("partitioner.class", "com.snow.kafka.producer.SimplePartitioner");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return properties;
    }
    
    public void sendMessage() {
    	//设置属性参数
        Properties properties = createProperties();
        //设置producer
        Producer<String, String> producer = new KafkaProducer<String, String>(properties);
        int i = 0;
        try {
            while (true) {
                TimeUnit.SECONDS.sleep(2);
                String key = Integer.toString(i);
                String value = "times: " + key;
                ProducerRecord<String, String> record = new ProducerRecord<String, String>("test", key, value);
                producer.send(record, new Callback() {

                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception e) {
                        if (e != null) {
                            logger.warn("send record error {}", e);
                        }
                        logger.info("offset: {}, partition: {}", metadata.offset(), metadata.partition());
                    }
                });
                i++;
            }
        } catch (Exception e) {
            logger.warn("{}", e);
        } finally {
            producer.close();
        }

    }
}
