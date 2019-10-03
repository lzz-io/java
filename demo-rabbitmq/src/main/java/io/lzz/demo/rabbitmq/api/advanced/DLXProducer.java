/*
 * Copyright qq:1219331697
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.lzz.demo.rabbitmq.api.advanced;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 * DLX 死信队列
 * 
 * @author q1219331697
 *
 */
public class DLXProducer {

	private static final String exchange = "demo.dlx.biz.topic";
	private static final String type = "topic";
	private static final String routingKey = "demo.dlx.dlx.#";
	private static final String queue_dlx = "demo.dlx.dlx.topic.queue";

	private static final String exchange_dlx = "demo.dlx.dlx.topic";
	private static final String routingKey_dlx = "demo.dlx.biz.#";
	private static final String queue = "demo.dlx.biz.topic.queue";

	public void send() throws Exception {

		ConnectionFactory connectionFactory = new ConnectionFactory();
		String host = "localhost";
		String username = "guest";
		String password = "guest";
		int port = 5672;
		connectionFactory.setHost(host);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setPort(port);
		Connection connection = connectionFactory.newConnection();

		Channel channel = connection.createChannel();

		// 删除exchange，测试用
		// channel.exchangeDelete(exchange);
		// channel.queueDelete(queue_dlx);
		// channel.exchangeDelete(exchange_dlx);
		// channel.queueDelete(queue);

		// 定义biz exchange
		channel.exchangeDeclare(exchange, type, true, false, null);

		Map<String, Object> arguments = new HashMap<>(0);
		// 设置死信交换机
		arguments.put("x-dead-letter-exchange", exchange_dlx);
		// 设置死信routingKey
		arguments.put("x-dead-letter-routing-key", routingKey_dlx);
		// 设置消息的过期时间， 单位是毫秒
		// arguments.put("x-message-ttl", 15000);
		// 定义dlx queue
		channel.queueDeclare(queue_dlx, true, false, false, arguments);

		// 绑定routingKey
		channel.queueBind(queue_dlx, exchange, routingKey);

		// 定义dlx exchange
		channel.exchangeDeclare(exchange_dlx, type, true, false, null);
		// 定义biz queue
		channel.queueDeclare(queue, true, false, false, null);
		// 绑定routingKey
		channel.queueBind(queue, exchange_dlx, routingKey_dlx);

		// 消息持久化
		AMQP.BasicProperties props = new AMQP.BasicProperties().builder()//
				.deliveryMode(2) // 持久化
				.contentEncoding("UTF-8")// 编码
				.expiration("10000")// 有效期
				.build();
		for (int i = 0; i < 50; i++) {
			// 发送消息到routingKey
			String msg = "ttl msg! " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
			channel.basicPublish(exchange, routingKey, props, msg.getBytes());
		}

		// 非必须
		channel.close();
		connection.close();
	}

	public static void main(String[] args) throws Exception {
		new DLXProducer().send();
	}
}
