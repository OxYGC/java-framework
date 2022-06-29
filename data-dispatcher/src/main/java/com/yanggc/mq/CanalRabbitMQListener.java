package com.yanggc.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.yanggc.constant.MQConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Description:
 *
 * @author: YangGC
 */
@Slf4j
@Component
public class CanalRabbitMQListener {
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = MQConstant.CANAL_QUEUE, durable = "true"),
                    exchange = @Exchange(value = MQConstant.CANAL_EXCHANGE),
                    key = MQConstant.CANAL_ROUTING_KEY
            )
    })
    public void handleCanalDataChange(String message) {
        System.out.println(message);
        log.error("[canal] 接收消息: {}", JSON.toJSONString(message));
    }


    /**
     * 私教课消课
     * 监听延迟-队列消息
     *
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = MQConstant.CANAL_QUEUE, durable = "true"),
                    exchange = @Exchange(value = MQConstant.CANAL_EXCHANGE),
                    key = MQConstant.CANAL_ROUTING_KEY
            )
    })
    public void receiveD1(Message message, Channel channel) throws IOException {
        String req = new String(message.getBody(), "utf-8");
        log.info("当前时间111111111111111：{},延时队列收到消息：{}", LocalDateTime.now(),req );
        JSONObject dataJson = JSONObject.parseObject(req);
        log.error("dataJson>>>"+dataJson.toJSONString());


        log.info("当前时间：{},延时队列收到消息：channel{}", LocalDateTime.now(), channel.toString());

    }

}
