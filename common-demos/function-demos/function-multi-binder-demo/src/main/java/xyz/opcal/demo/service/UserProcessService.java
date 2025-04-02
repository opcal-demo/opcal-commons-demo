package xyz.opcal.demo.service;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import xyz.opcal.demo.model.User;

@Service
public class UserProcessService {

	Logger consumerLogger = LoggerFactory.getLogger("consumer");

	private AtomicLong idGenerator = new AtomicLong();
	private AtomicLong messageCounter = new AtomicLong();

	public long consumeCount() {
		return messageCounter.get();
	}

	@Bean
	public Function<xyz.opcal.tools.response.result.User, User> tranfer() {
		return this::userTranfer;
	}

	@Bean
	public Function<KStream<String, User>, KStream<String, Long>> ageCount() {
		return kStream -> kStream.map((k, user) -> new KeyValue<>(getAgeGroup(user), 0L))//
				.groupByKey(Grouped.with(Serdes.String(), Serdes.Long()))//
				.windowedBy(TimeWindows.ofSizeAndGrace(Duration.ofSeconds(30), Duration.ofSeconds(0))) //
				.count(Materialized.as("mt-uac"))//
				.toStream().map((key, value) -> new KeyValue<>(key.key(), value));
	}

	@Bean
	public Consumer<KTable<String, Long>> countConsume() {
		return counts -> counts.toStream().foreach(this::count);
	}

	User userTranfer(xyz.opcal.tools.response.result.User user) {
		consumerLogger.info("**** tranfer user [{}]", user);
		return new User(idGenerator.incrementAndGet(), user.getName().getFirst(), user.getName().getLast(), user.getGender(), user.getDob().getAge());
	}

	void count(String key, long value) {
		consumerLogger.info("**** [{}]={}", key, value);
		messageCounter.incrementAndGet();
	}

	static String getAgeGroup(User user) {
		if (user.age() <= 10) {
			return "0-10";
		}
		if (user.age() <= 20) {
			return "11-20";
		}
		if (user.age() <= 30) {
			return "21-30";
		}
		if (user.age() <= 40) {
			return "31-40";
		}
		if (user.age() <= 50) {
			return "41-50";
		}
		if (user.age() <= 60) {
			return "51-60";
		}
		if (user.age() <= 70) {
			return "61-70";
		}
		if (user.age() <= 80) {
			return "71-80";
		}
		if (user.age() <= 90) {
			return "81-90";
		}
		if (user.age() <= 100) {
			return "91-100";
		}
		return "100+";
	}
}
