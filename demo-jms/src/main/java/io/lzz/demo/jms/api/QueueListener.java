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
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import io.lzz.demo.jms.config.Constants;

/**
 * @author q1219331697
 *
 */
public class QueueListener {

	public void receive() {

		Connection connection = MqConnectionFactory.getConnection();
		Session session = null;

		try {
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			Queue queue = session.createQueue(Constants.QUEUE_TEST);
			MessageConsumer consumer = session.createConsumer(queue);
			MessageListener listener = new MessageListener() {

				@Override
				public void onMessage(Message message) {
					TextMessage textMessage = (TextMessage) message;
					try {
						String text = textMessage.getText();
						System.out.println(text);

						message.acknowledge();
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			};
			consumer.setMessageListener(listener);

			// session,connection listener模式下不能关闭
			// session.close();
			// connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}

	public static void main(String[] args) {
		new QueueListener().receive();
	}
}
