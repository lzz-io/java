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

package io.lzz.demo.jms.api;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import io.lzz.demo.jms.config.Constants;

/**
 * @author q1219331697
 *
 */
public class TopicListener {

	public void receive() {

		String brokerURL = "tcp://127.0.0.1:61616";
		String userName = "admin";
		String password = "admin";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(userName, password, brokerURL);

		Connection connection = null;
		Session session = null;

		try {
			connection = connectionFactory.createConnection();
			connection.start();

			// session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic(Constants.TOPIC_TEST);
			MessageConsumer consumer = session.createConsumer(topic);

			consumer.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					try {
						TextMessage textMessage = (TextMessage) message;
						String text = textMessage.getText();
						System.out.println(text);
						message.acknowledge();
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});

			// session,connection listener模式下不能关闭
			// session.close();
			// connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}

	public static void main(String[] args) {
		new TopicListener().receive();

		while (true) {
			try {
				Thread.sleep(120000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
