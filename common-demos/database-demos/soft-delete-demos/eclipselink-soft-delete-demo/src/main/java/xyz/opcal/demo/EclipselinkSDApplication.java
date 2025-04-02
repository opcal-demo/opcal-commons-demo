package xyz.opcal.demo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;

@SpringBootApplication
@EnableJpaAuditing(dateTimeProviderRef = "utc8DateTimeProvider")
public class EclipselinkSDApplication {

	public static void main(String[] args) {
		SpringApplication.run(EclipselinkSDApplication.class, args);
	}

	@Bean
	public DateTimeProvider utc8DateTimeProvider() {
		return () -> Optional.of(LocalDateTime.now(ZoneOffset.of("+8")));
	}

	@Bean
	public LoadTimeWeaver loadTimeWeaver() {
		return new InstrumentationLoadTimeWeaver();
	}

}
