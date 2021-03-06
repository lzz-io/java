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

package io.lzz.demo.jms.topic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.lzz.demo.jms.topic.TopicProducer;

/**
 * @author q1219331697
 *
 */
@SpringBootTest(classes = TopicProducer.class)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
public class TopicProducerTest {

	@Autowired
	private TopicProducer topicProducer;

	@Test
	public void testDoSend() throws InterruptedException {
		topicProducer.doSend();
		
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				public void run() {
					topicProducer.doSend();
				}
			}).run();
		}

		Thread.sleep(10000);
	}

}
