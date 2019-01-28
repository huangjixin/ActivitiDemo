package com.zhiyesoft.activiti.demo.config;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zhiyesoft.activiti.demo.service.impl.SelfTopicTestConsumerHandler;

@Configuration
public class RocketMQConfig {

	@Autowired
	private DefaultMQProducer producer;
	
	@Autowired
	private DefaultMQPushConsumer consumer;
	
	@Autowired
	private SelfTopicTestConsumerHandler selfTopicTestConsumerHandler;
	
	@Bean
	public DefaultMQProducer getDefaultMQProducer() {
		DefaultMQProducer producer = new DefaultMQProducer("myproducer");
		producer.setNamesrvAddr("192.168.9.220:9876;192.168.9.220:9877;");
		return producer;
	}
	
	@Bean
	public DefaultMQPushConsumer getDefaultMQPushConsumer() {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("myconsumer");
		consumer.setNamesrvAddr("192.168.9.220:9876;192.168.9.220:9877;");
		//这里设置的是一个consumer的消费策略
        //CONSUME_FROM_LAST_OFFSET 默认策略，从该队列最尾开始消费，即跳过历史消息
        //CONSUME_FROM_FIRST_OFFSET 从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
        //CONSUME_FROM_TIMESTAMP 从某个时间点开始消费，和setConsumeTimestamp()配合使用，默认是半个小时以前
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		return consumer;
	}
	
	@Bean
	public SelfTopicTestConsumerHandler getSelfTopicTestConsumerHandler() {
		SelfTopicTestConsumerHandler consumerHandler = new SelfTopicTestConsumerHandler();
		return consumerHandler;
	}
	
	

	@PostConstruct
	public void postConstruct() {
		try {
			//设置consumer所订阅的Topic和Tag，*代表全部的Tag
	        consumer.subscribe("SELF_TEST_TOPIC", "*");

	        //设置一个Listener，主要进行消息的逻辑处理
	        consumer.registerMessageListener(this.selfTopicTestConsumerHandler);
	        
	        producer.start();
	      //调用start()方法启动consumer
	        consumer.start();
	        
		} catch (MQClientException e) {
			e.printStackTrace();
		}
	}
}
