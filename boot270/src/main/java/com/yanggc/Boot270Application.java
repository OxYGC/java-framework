package com.yanggc;

import com.alibaba.fastjson.TypeReference;
import com.yanggc.pojo.OrderPaidEvent;
import com.yanggc.pojo.ProductWithPayload;
import com.yanggc.pojo.User;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQLocalRequestCallback;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 自定义非标的RocketMQTemplate, Bean名与所定义的类名相同(但首字母小写)
     */
    @Resource(name = "extRocketMQTemplate")
    private RocketMQTemplate extRocketMQTemplate;


    private final static String STRING_TOPIC = "string-topic";

    private final static String USER_TOPIC = "user-topic";

    private final static String ORDER_TOPIC = "order-paid-topic";

    private final static String MSG_EXT_TOPIC = "message-ext-topic";

    private final static String TRANS_TOPIC = "spring-transaction-topic";

    private final static String STRING_REQUEST_TOPIC = "tringRequestTopic:tagA";

    private final static String BYTES_REQUEST_TOPIC = "bytesRequestTopic:tagA";

    private final static String OBJECT_REQUEST_TOPIC = "objectRequestTopic:tagA";

    private final static String GENERIC_REQUEST_TOPIC = "genericRequestTopic:tagA";




    @Override
    public void run(String... args) throws Exception {
        System.out.println("..................");
        SendResult sendResult;
        // 发送字符串
//        sendResult = rocketMQTemplate.syncSend(STRING_TOPIC, "Hello, World!");
//        System.out.println(sendResult);

        //发送对象
        sendResult = rocketMQTemplate.syncSend(USER_TOPIC, new User().setUserAge((byte) 18).setUserName("Kitty"));
        System.out.printf("syncSend1 to topic %s sendResult=%s %n", USER_TOPIC, sendResult);

        sendResult = rocketMQTemplate.syncSend(USER_TOPIC, MessageBuilder.withPayload(
                new User().setUserAge((byte) 21).setUserName("Lester")).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build());
        System.out.printf("syncSend1 to topic %s sendResult=%s %n", USER_TOPIC, sendResult);

        // Use the extRocketMQTemplate
        sendResult = rocketMQTemplate.syncSend(STRING_TOPIC, MessageBuilder.withPayload("Hello, World!2222".getBytes()).build());
        System.out.printf("extRocketMQTemplate.syncSend1 to topic %s sendResult=%s %n", STRING_TOPIC, sendResult);

        // Send string with spring Message
        sendResult = rocketMQTemplate.syncSend(STRING_TOPIC, MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
        System.out.printf("syncSend2 to topic %s sendResult=%s %n", STRING_TOPIC, sendResult);


        // 发送消息对异常处理
        rocketMQTemplate.asyncSend(ORDER_TOPIC, new OrderPaidEvent("T_001", new BigDecimal("88.00")), new SendCallback() {
            @Override
            public void onSuccess(SendResult var1) {
                System.out.printf("async onSucess SendResult=%s %n", var1);
            }
            @Override
            public void onException(Throwable var1) {
                System.out.printf("async onException Throwable=%s %n", var1);
            }
        });

        // 携带tag标签进行发送  tag0 will not be consumer-selected
        rocketMQTemplate.convertAndSend(MSG_EXT_TOPIC + ":tag0", "I'm from tag0");
        System.out.printf("syncSend topic %s tag %s %n", MSG_EXT_TOPIC, "tag0");

        rocketMQTemplate.convertAndSend(MSG_EXT_TOPIC + ":tag1", "I'm from tag1");
        System.out.printf("syncSend topic %s tag %s %n", MSG_EXT_TOPIC, "tag1");


        // 批量发送字符串
        testBatchMessages();
        // 批量发送字符串订单
        testSendBatchMessageOrderly();

        //使用 rocketMQTemplate 发送事务消息
        testRocketMQTemplateTransaction();

        // 发送事务
        testExtRocketMQTemplateTransaction();

        //   使用同步模式发送请求 获得字符串类型的响应
        String replyString = rocketMQTemplate.sendAndReceive(STRING_REQUEST_TOPIC, "request string", String.class);
        System.out.printf("send %s and receive %s %n", "request string", replyString);



        //   同步模式携带超时时间发送请求并且获得byte[]类型的响应
        byte[] replyBytes = rocketMQTemplate.sendAndReceive(BYTES_REQUEST_TOPIC, MessageBuilder.withPayload("request byte[]").build(), byte[].class, 3000);
        System.out.printf("send %s and receive %s %n", "request byte[]", new String(replyBytes));

        // 同步模式携带hashkey参数发送请求并且获得User类型的响应
        User requestUser = new User().setUserAge((byte) 9).setUserName("requestUserName");
        User replyUser = rocketMQTemplate.sendAndReceive(OBJECT_REQUEST_TOPIC, requestUser, User.class, "order-id");
        System.out.printf("send %s and receive %s %n", requestUser, replyUser);
        // 同步模式携带超时时间和延迟级别参数 发送请求并且获得通用类型的响应

        //setStartDeliverTime
        ProductWithPayload<String> replyGenericObject = rocketMQTemplate.sendAndReceive(GENERIC_REQUEST_TOPIC, "request generic",
                new TypeReference<ProductWithPayload<String>>() {
                }.getType(), 30000, 2);
        System.out.printf("send %s and receive %s %n", "request generic", replyGenericObject);

        //   异步发送请求并且获得一个String类型的响应
        rocketMQTemplate.sendAndReceive(STRING_REQUEST_TOPIC, "request string", new RocketMQLocalRequestCallback<String>() {
            @Override public void onSuccess(String message) {
                System.out.printf("send %s and receive %s %n", "request string", message);
            }

            @Override public void onException(Throwable e) {
                e.printStackTrace();
            }
        });
        // 异步发送请求并且获得一个user类型的响应
        rocketMQTemplate.sendAndReceive(OBJECT_REQUEST_TOPIC, new User().setUserAge((byte) 9).setUserName("requestUserName"), new RocketMQLocalRequestCallback<User>() {
            @Override public void onSuccess(User message) {
                System.out.printf("send user object and receive %s %n", message.toString());
            }
            @Override public void onException(Throwable e) {
                e.printStackTrace();
            }
        }, 5000);
        org.apache.rocketmq.common.message.Message message = new org.apache.rocketmq.common.message.Message();
    }



    //批量发送消息
    private void testBatchMessages() {
        List<Message> msgs = new ArrayList<Message>();
        for (int i = 0; i < 10; i++) {
            msgs.add(MessageBuilder.withPayload("Hello RocketMQ Batch Msg#" + i).
                    setHeader(RocketMQHeaders.KEYS, "KEY_" + i).build());
        }
        SendResult sr = rocketMQTemplate.syncSend(STRING_TOPIC, msgs, 60000);
        System.out.printf("--- Batch messages send result :" + sr);
    }

    //按顺序发送消息
    private void testSendBatchMessageOrderly() {
        for (int q = 0; q < 4; q++) {
            // send to 4 queues
            List<Message> msgs = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                int msgIndex = q * 10 + i;
                String msg = String.format("Hello RocketMQ Batch Msg#%d to queue: %d", msgIndex, q);
                msgs.add(MessageBuilder.withPayload(msg)
                        .setHeader(RocketMQHeaders.KEYS, "KEY_" + msgIndex).build());
            }
            SendResult sr = rocketMQTemplate.syncSendOrderly(STRING_TOPIC, msgs, q + "", 60000);
            System.out.println("--- Batch messages orderly to queue :" + sr.getMessageQueue().getQueueId() + " send result :" + sr);
        }
    }


    private void testRocketMQTemplateTransaction() throws MessagingException {
        String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            try {

                Message msg = MessageBuilder.withPayload("rocketMQTemplate transactional message " + i).
                        setHeader(RocketMQHeaders.TRANSACTION_ID, "KEY_" + i).build();
                SendResult sendResult = rocketMQTemplate.sendMessageInTransaction(
                        STRING_TOPIC + ":" + tags[i % tags.length], msg, null);
                System.out.printf("------rocketMQTemplate send Transactional msg body = %s , sendResult=%s %n",
                        msg.getPayload(), sendResult.getSendStatus());

                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void testExtRocketMQTemplateTransaction() throws MessagingException {
        for (int i = 0; i < 10; i++) {
            try {
                Message msg = MessageBuilder.withPayload("extRocketMQTemplate transactional message " + i).
                        setHeader(RocketMQHeaders.TRANSACTION_ID, "KEY_" + i).build();
                SendResult sendResult = rocketMQTemplate.sendMessageInTransaction(
                        TRANS_TOPIC, msg, null);
                System.out.printf("------ExtRocketMQTemplate send Transactional msg body = %s , sendResult=%s %n",
                        msg.getPayload(), sendResult.getSendStatus());

                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
