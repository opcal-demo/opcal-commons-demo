package xyz.opcal.demo.function;

import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import xyz.opcal.demo.model.User;

@Configuration
public class UserFunction {

	Logger consumerLogger = LoggerFactory.getLogger("consumer");

	@Bean
	Function<KStream<String, User>, KStream<String, Long>> userProcessor() {
		return kStream -> kStream.map((k, user) -> new KeyValue<>(getAgeGroup(user), 0L))//
				.groupByKey(Grouped.with(Serdes.String(), Serdes.Long()))//
				.count(Materialized.as("uac"))//
				.toStream();
	}

	@Bean
	Consumer<KTable<String, Long>> ageCount() {
		return counts -> counts.toStream() //
				.foreach((key, value) -> consumerLogger.info("**** [{}]={}", key, value));
	}

	String getAgeGroup(User user) {
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
