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

package io.lzz.demo.spring.batch.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.lzz.demo.spring.batch.entity.User;

/**
 * @author q1219331697
 *
 */
public class MyItemProcessor implements ItemProcessor<User, String> {

	private static final Logger log = LoggerFactory.getLogger(MyItemProcessor.class);

	private Integer count = 0;

	@Override
	public String process(User user) throws Exception {
		count++;
		User user2 = new User();
		BeanUtils.copyProperties(user, user2);
		user2.setId(count);
		// SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss.SSS");
		// user2.setCreateTime(simpleDateFormat.format(new Date()));
		user2.setCreateTime(new Date());
		log.debug("user:{}", user);
		log.debug("user2:{}", user2);

		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(user2);
		log.debug(string);
		return string;
	}

}