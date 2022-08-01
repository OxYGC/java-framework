package com.yanggc.config;

import org.apache.rocketmq.spring.annotation.ExtRocketMQTemplateConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

/**
 * Description:
 * RocketMQ-Spring 2.1.0版本之后，注解@RocketMQTransactionListener不能设置txProducerGroup、ak、sk，这些值均与对应的RocketMQTemplate保持一致。
 * 换言之，由于不同事务流程的事务消息需要使用不同的生产者组来发送，故为了设置生产者组名。需要通过@ExtRocketMQTemplateConfiguration注解来定义非标的RocketMQTemplate。
 * 定义非标的RocketMQTemplate时可自定义相关属性，如果不定义，它们取全局的配置属性值或默认值。由于该注解已继承自@Component注解，
 * 故无需开发者重复添加即可完成相应的实例化。这里我们自定义该非标实例的生产者组名
 * @author: YangGC
 */

@ExtRocketMQTemplateConfiguration(nameServer = "127.0.0.1:9876", tlsEnable = "false")
public class ExtRocketMQTemplate extends RocketMQTemplate {

}
