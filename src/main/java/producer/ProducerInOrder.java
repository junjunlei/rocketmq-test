package producer;

import io.netty.channel.DefaultChannelId;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author zhanbo
 * @Description 顺序消息生成
 * @Date 2021-10-09 13:56
 * @Version 1.0
 **/
public class ProducerInOrder
{

	public static void main(String[] args) throws Exception
	{
		DefaultMQProducer producer = new DefaultMQProducer("producer_02");
		producer.setNamesrvAddr("59.110.164.184:9876");
		//修改默认超时时间
		producer.setSendMsgTimeout(90000);
		DefaultChannelId.newInstance();
		producer.start();
		String[] tags = { "TagA", "TagB", "TagC" };
		List<Order> list = Order.buildOrderList();
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = format.format(date);
		for (int i = 0; i < 10; i++)
		{
			String body = dateStr + " Hello RocketMQ " + list.get(i);
			Message message = new Message("TopicInOrder", tags[i % tags.length], "KEY" + i, body.getBytes());

			SendResult result = producer.send(message, new MessageQueueSelector()
			{

				public MessageQueue select(List<MessageQueue> mqs, Message message, Object o)
				{
					//订单id
					Long id = (Long) o;
					//根据订单id选择队列
					long index = id % mqs.size();
					return mqs.get((int) index);
				}
				//订单id
			}, list.get(i).getOrderId());
			System.out.println(result);
		}
		producer.shutdown();
	}
}
