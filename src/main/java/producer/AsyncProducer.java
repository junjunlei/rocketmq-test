package producer;

import io.netty.channel.DefaultChannelId;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * @Author zhanbo
 * @Description 异步消息发送
 * @Date 2021-10-08 19:35
 * @Version 1.0
 **/
public class AsyncProducer
{

	public static void main(String[] args) throws Exception
	{
		DefaultMQProducer producer = new DefaultMQProducer("group_02");
		producer.setNamesrvAddr("59.110.164.184:9876");
		DefaultChannelId.newInstance();
		producer.start();
		producer.setRetryTimesWhenSendAsyncFailed(0);
		for (int i = 0; i < 10; i++)
		{
			final int index=i;
			Message message = new Message("topic_02", "tag_02", "ooo","异步消息测试哈哈哈111".getBytes("UTF-8"));
			//接收异步返回结果回调
			producer.send(message, new SendCallback()
			{

				public void onSuccess(SendResult sendResult)
				{
					System.out.printf("%-10d OK %s %n",index,sendResult.getMsgId());
				}

				public void onException(Throwable throwable)
				{
					System.out.printf("%-10d OK %s %n",index,throwable);
					throwable.printStackTrace();
				}
			});
			//睡一下，防止producer被关闭
			TimeUnit.SECONDS.sleep(1);
		}
		producer.shutdown();
	}
}
