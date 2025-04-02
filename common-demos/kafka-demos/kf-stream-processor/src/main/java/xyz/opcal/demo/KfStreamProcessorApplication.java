package xyz.opcal.demo;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class KfStreamProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(KfStreamProcessorApplication.class, args);
	}

	@Bean
	NewTopic newUser() {
		return TopicBuilder.name("kf_new_user").partitions(3).replicas(1).build();
	}

	@Bean
	NewTopic topicAgeCount() {
		return TopicBuilder.name("kf_new_user_age_count").partitions(3).replicas(1).build();
	}

}
