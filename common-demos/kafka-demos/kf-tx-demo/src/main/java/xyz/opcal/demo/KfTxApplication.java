package xyz.opcal.demo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing(dateTimeProviderRef = "utc8DateTimeProvider")
public class KfTxApplication {

	public static void main(String[] args) {
		SpringApplication.run(KfTxApplication.class, args);
	}

	@Bean
	public DateTimeProvider utc8DateTimeProvider() {
		return () -> Optional.of(LocalDateTime.now(ZoneOffset.of("+8")));
	}

	@Bean
	public NewTopic randomuser() {
		return TopicBuilder.name("randomuser").partitions(3).replicas(1).build();
	}

	@Bean
	@Primary
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
