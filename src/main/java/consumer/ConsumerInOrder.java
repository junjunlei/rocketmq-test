package consumer;

import io.netty.channel.DefaultChannelId;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author zhanbo
 * @Description 顺序消息消费，带事物方式（应用可控制offset什么时候提交）
 * @Date 2021-10-15 15:29
 * @Version 1.0
 **/
public class ConsumerInOrder
{

	public static void main(String[] args) throws MQClientException
	{
		final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_in_order");
		consumer.setNamesrvAddr("59.110.164.184:9876");
		consumer.setConsumeTimeout(900000);

		//如果consumer第一次启动是从队列头还是队列尾开始消费
		//如果非第一次启动，还是按上次消费的位置继续消费
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

		consumer.subscribe("TopicInOrder","TagA||TagB||TagC");

		//这里可以抽取出来
		consumer.registerMessageListener(new MessageListenerOrderly()
		{

			@Override
			public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext context)
			{
				context.setAutoCommit(true);
				for (MessageExt msg : list)
				{
					System.out.println("consumerThread="+ Thread.currentThread().getName()+"queueId="+msg.getQueueId()+" ,  content: "+new String(msg.getBody()));
				}
				try
				{
					TimeUnit.SECONDS.sleep(1);
				}catch (Exception e){

				}
				return ConsumeOrderlyStatus.SUCCESS;
			}
		});

		DefaultChannelId.newInstance();
		consumer.start();
		System.out.println("Consumer started");

	}
}
