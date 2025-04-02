package xyz.opcal.demo;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class FunctionMultiBinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(FunctionMultiBinderApplication.class, args);
	}

	@Bean
	public NewTopic topicNewUser() {
		return TopicBuilder.name("mt_kf_user").partitions(3).replicas(1).build();
	}

	@Bean
	public NewTopic topicAgeCount() {
		return TopicBuilder.name("mt_kf_user_age_count").partitions(3).replicas(1).build();
	}

}
