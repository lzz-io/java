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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author q1219331697
 *
 */
@Service
public class QueueConsumer {

	private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);

//	@JmsListener(destination = Constants.QUEUE_TEST)
	public void doRec(String msg) {
		log.info(">>> get queue {}", msg);
		log.info("{}", Thread.currentThread());
	}
}
