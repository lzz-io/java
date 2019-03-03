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

package io.lzz.demo.spring.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import io.lzz.demo.spring.batch.entity.User;

/**
 * @author q1219331697
 *
 */
@Configuration
public class BatchConfig {

	@Autowired
	@Qualifier("step0")
	private Step step0;

	@Autowired
	@Qualifier("step1")
	private Step step1;

	@Autowired
	@Qualifier("step2MasterStep")
	private TaskletStep step2MasterStep;

	@Autowired
	@Qualifier("step3")
	private Step step3;

	@Bean
	public FlatFileItemReader<User> reader() {
		BeanWrapperFieldSetMapper<User> mapper = new BeanWrapperFieldSetMapper<>();
		mapper.setTargetType(User.class);
		return new FlatFileItemReaderBuilder<User>() //
				.name("reader") //
				.encoding("UTF-8") //
				.resource(new ClassPathResource("data.csv")) //
				.delimited() //
				// .quoteCharacter(',')//
				.delimiter(",")//
				.names(new String[] { "id", "username", "createTime" }) //
				.linesToSkip(1)//
				.fieldSetMapper(mapper) //
				.build();
	}

	@Bean
	public TaskExecutor taskExecutor() {
		// ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		// taskExecutor.setThreadNamePrefix("spring_batch_");
		// taskExecutor.setCorePoolSize(50);
		// return taskExecutor;
		return new SimpleAsyncTaskExecutor("spring_batch_");
		// return new SyncTaskExecutor();
	}

	// @Bean
	public Flow splitFlow() {
		return new FlowBuilder<SimpleFlow>("splitFlow")//
				.split(taskExecutor())//
				.add(flow2(), flow3())//
				.build();
	}

	// @Bean
	public Flow flow2() {
		return new FlowBuilder<SimpleFlow>("flow2")//
				.start(step2MasterStep)//
				.build();
	}

	// @Bean
	public Flow flow3() {
		return new FlowBuilder<SimpleFlow>("flow3")//
				.start(step3)//
				.build();
	}

	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory) {
		return jobBuilderFactory.get("job")//
				.incrementer(new RunIdIncrementer())// 使每个job的运行id都唯一,开发环境使用
				// .preventRestart()//
				.flow(step0)//
				.next(step1)//
				// .next(step2MasterStep)//
				// .next(step3)
				.next(splitFlow())//
				.end()//
				.build(); // builds Job instance
	}

}
