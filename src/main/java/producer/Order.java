package producer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhanbo
 * @Description 订单数据
 * @Date 2021-10-14 15:33
 * @Version 1.0
 **/
public class Order
{

	private Long orderId;

	private String desc;

	public Long getOrderId()
	{
		return orderId;
	}

	public void setOrderId(Long orderId)
	{
		this.orderId = orderId;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public Order(Long orderId, String desc)
	{
		this.orderId = orderId;
		this.desc = desc;
	}

	public Order()
	{
	}

	@Override
	public String toString()
	{
		return "Order{" + "orderId=" + orderId + ", desc='" + desc + '\'' + '}';
	}

	public static List<Order> buildOrderList()
	{
		List<Order> list=new ArrayList<Order>();

		Order order = new Order();
		order.setOrderId(1L);
		order.setDesc("创建订单");
		list.add(order);

		order = new Order();
		order.setOrderId(2L);
		order.setDesc("创建订单");
		list.add(order);

		order = new Order();
		order.setOrderId(1L);
		order.setDesc("订单付款");
		list.add(order);

		order = new Order();
		order.setOrderId(3L);
		order.setDesc("创建订单");
		list.add(order);

		order = new Order();
		order.setOrderId(2L);
		order.setDesc("订单付款");
		list.add(order);

		order = new Order();
		order.setOrderId(3L);
		order.setDesc("订单付款");
		list.add(order);


		order = new Order();
		order.setOrderId(2L);
		order.setDesc("订单完成");
		list.add(order);

		order = new Order();
		order.setOrderId(1L);
		order.setDesc("订单推送");
		list.add(order);

		order = new Order();
		order.setOrderId(2L);
		order.setDesc("订单完成");
		list.add(order);


		order = new Order();
		order.setOrderId(1L);
		order.setDesc("订单完成");
		list.add(order);

		return list;
	}
}
