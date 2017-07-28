/*
 * @(#) ConsumerMainTest.java 2016/09/26
 * 
 * Copyright 2016 snow.com, Inc. All rights reserved.
 */
package com.snow.kafka.consumer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author cgt
 * @version 2017/07/26
 */
@Component
public class ConsumerMainTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"kafkacontext.xml"});
        ConsumerTest consumerTest = context.getBean(ConsumerTest.class);
        // consumerTest.simpleConsumer();
        // consumerTest.consumer();
        consumerTest.consumerExactly();
    }

}
