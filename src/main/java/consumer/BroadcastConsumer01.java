package consumer;

import io.netty.channel.DefaultChannelId;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @Author zhanbo
 *
 * 广播模式所有订阅topic的消费者都会消费
 * @Description 广播模式消费者1
 * @Date 2021-10-09 13:36
 * @Version 1.0
 **/
public class BroadcastConsumer01
{
	public static void main(String[] args) throws Exception
	{
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("broadcastConsumer");
		consumer.setNamesrvAddr("59.110.164.184:9876");
		consumer.setConsumeTimeout(900000);
		//订阅topic
		consumer.subscribe("topic_02","*");
		//负载均衡模式
		consumer.setMessageModel(MessageModel.BROADCASTING);
		//注册回调函数，处理消息
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			public ConsumeConcurrentlyStatus consumeMessage(
					List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
				for (MessageExt messageExt : list) {
					System.out.println("广播模式消费者1收到的消息为： "+new String(messageExt.getBody()));
				}
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		DefaultChannelId.newInstance();
		consumer.start();
		System.out.println("Consumer started");
	}
}
