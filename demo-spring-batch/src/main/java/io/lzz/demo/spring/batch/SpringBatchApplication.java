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

package io.lzz.demo.spring.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author q1219331697
 *
 */
@SpringBootApplication
public class SpringBatchApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringBatchApplication.class);

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBatchApplication.class, args);
		log.info("application started");
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
