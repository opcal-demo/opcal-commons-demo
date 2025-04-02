package xyz.opcal.demo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(dateTimeProviderRef = "utc8DateTimeProvider")
public class FlywayMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlywayMysqlApplication.class, args);
	}

	@Bean
	public DateTimeProvider utc8DateTimeProvider() {
		return () -> Optional.of(LocalDateTime.now(ZoneOffset.of("+8")));
	}

}
