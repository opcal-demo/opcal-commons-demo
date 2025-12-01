package xyz.opcal.demo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(dateTimeProviderRef = "utc8DateTimeProvider")
public class RbTxApplication {

	public static final String EXCHANGE_NAME = "commons-demo";
	public static final String QUEUE_NAME = "randomuser";

	public static void main(String[] args) {
		SpringApplication.run(RbTxApplication.class, args);
	}

	@Bean(EXCHANGE_NAME)
	public Exchange exchange() {
		return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(false).autoDelete().build();
	}

	@Bean(QUEUE_NAME)
	public Queue queue() {
		return new Queue(QUEUE_NAME, false, false, true);
	}

	@Bean
	Binding demoBinding(@Qualifier(QUEUE_NAME) Queue queue, @Qualifier(EXCHANGE_NAME) Exchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("commons.demo.#").noargs();
	}

	@Bean
	public MessageConverter jsonConverter() {
		return new JacksonJsonMessageConverter();
	}

	@Bean
	public DateTimeProvider utc8DateTimeProvider() {
		return () -> Optional.of(LocalDateTime.now(ZoneOffset.of("+8")));
	}
}
