package com.practice.kafka.configuration;

import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

	@Bean
	public NewTopic createTopic() {
		
		return TopicBuilder.name("product-created-events-topic")
				.partitions(3)   //with 3 partitions we can start 3 MS consumers at a time
				.replicas(3)     //3 replicas means 1 copy on each broker which improves durability
				.configs(Map.of("min.insync.replicas","2"))  //atleast 2 replicas must acknowledge that data is stored successfully
				.build();
	}
}
