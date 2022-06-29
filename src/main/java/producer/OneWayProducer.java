package producer;

import io.netty.channel.DefaultChannelId;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @Author zhanbo
 * @Description 单向消息，不关心返回结果
 * @Date 2021-10-09 12:53
 * @Version 1.0
 **/
public class OneWayProducer
{

	public static void main(String[] args) throws Exception
	{
		DefaultMQProducer producer = new DefaultMQProducer("producer_01");
		producer.setNamesrvAddr("59.110.164.184:9876");
		//修改默认超时时间
		producer.setSendMsgTimeout(90000);
		DefaultChannelId.newInstance();
		producer.start();
		for (int i = 0; i <= 10; i++)
		{
			Message message = new Message("topic_03", "tag03", "测试一下这个鬼东西3".getBytes("UTF-8"));
			//超时时间默认3000ms
			producer.sendOneway(message);
		}
		producer.shutdown();
	}
}
