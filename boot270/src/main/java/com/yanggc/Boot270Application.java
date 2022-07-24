package com.yanggc;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author: YangGC
 */
@SpringBootApplication
public class Boot270Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Boot270Application.class, args);
    }

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    private final static String STRING_TOPIC= "string-topic";


    @Override
    public void run(String... args) throws Exception {
        System.out.println("..................");

        // 发送字符串
        SendResult sendResult = rocketMQTemplate.syncSend(STRING_TOPIC, "Hello, World!");

    }
}
