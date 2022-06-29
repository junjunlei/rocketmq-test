package producer;

import io.netty.channel.DefaultChannelId;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @Author zhanbo
 * @Description 同步消息发送
 * @Date 2021-10-08 15:57
 * @Version 1.0
 **/
public class SyncProducer
{

	public static void main(String[] args) throws Exception
	{
		DefaultMQProducer producer = new DefaultMQProducer("producer_01");
		producer.setNamesrvAddr("59.110.164.184:9876");
		DefaultChannelId.newInstance();
		producer.start();
		for(int i=0;i<10;i++){
			Message message = new Message("topic_01", "tag01", "测试一下这个鬼东西2".getBytes("UTF-8"));
			//超时时间默认3000ms
			SendResult sendResult = producer.send(message,900000);
			System.out.printf("%s%n",sendResult);
		}
		producer.shutdown();
	}
}
