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

package io.lzz.demo.jms.queue;

import javax.jms.Destination;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.lzz.demo.jms.config.Constants;

/**
 * @author q1219331697
 *
 */
@Service
@EnableScheduling
public class TopicProducer {

	private static final Logger log = LoggerFactory.getLogger(TopicProducer.class);

	@Autowired
	private JmsTemplate jmsTemplate;

	@Scheduled(cron = "*/1 * * * * *")
	public void doSend() {
		Destination destination = new ActiveMQTopic(Constants.TOPIC_TEST);

		String message = Thread.currentThread().getName() + ":" + Thread.currentThread().getId();

		jmsTemplate.convertAndSend(destination, message);
		log.info("<<< send {}", message);
	}

}
