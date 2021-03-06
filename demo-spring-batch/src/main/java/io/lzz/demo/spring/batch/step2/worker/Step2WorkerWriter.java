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

package io.lzz.demo.spring.batch.step2.worker;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.util.ClassUtils;

/**
 * 实现有问题，不能用于生产
 * 
 * @author q1219331697
 *
 */
public class Step2WorkerWriter<T> extends FlatFileItemWriter<T> {

	private static final Logger log = LoggerFactory.getLogger(Step2WorkerWriter.class);

	private ExecutionContext executionContext;

	public Step2WorkerWriter() {
		this.setExecutionContextName(ClassUtils.getShortName(Step2WorkerWriter.class));
		// setName(name);
		this.executionContext = new ExecutionContext();
	}

	@Override
	public void write(List<? extends T> items) throws Exception {
		super.open(executionContext);
		log.debug("executionContext={}", executionContext);
		super.write(items);
	}

}
