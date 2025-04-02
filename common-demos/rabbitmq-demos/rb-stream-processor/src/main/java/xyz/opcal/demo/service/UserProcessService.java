package xyz.opcal.demo.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import lombok.Getter;
import xyz.opcal.demo.model.AgeRange;

@Service
public class UserProcessService {

	Logger consumerLogger = LoggerFactory.getLogger("consumer");

	@Getter
	private Map<String, Integer> ageCounter = new ConcurrentHashMap<>();

	@Bean
	public Function<xyz.opcal.tools.response.result.User, AgeRange> ageAnalyze() {
		return this::consumeUser;
	}

	@Bean
	public Consumer<AgeRange> ageCount() {
		return this::consumeAgeRange;
	}

	AgeRange consumeUser(xyz.opcal.tools.response.result.User user) {
		consumerLogger.info("**** consume user [{}]", user);
		return new AgeRange(getAgeGroup(user));
	}

	synchronized void consumeAgeRange(AgeRange ageRange) {
		consumerLogger.info("**** consume AgeRange [{}]", ageRange);
		if (ageCounter.get(ageRange.range()) == null) {
			ageCounter.put(ageRange.range(), 1);
		} else {
			ageCounter.put(ageRange.range(), ageCounter.get(ageRange.range()) + 1);
		}
	}

	static String getAgeGroup(xyz.opcal.tools.response.result.User user) {
		if (user.getDob().getAge() <= 10) {
			return "0-10";
		}
		if (user.getDob().getAge() <= 20) {
			return "11-20";
		}
		if (user.getDob().getAge() <= 30) {
			return "21-30";
		}
		if (user.getDob().getAge() <= 40) {
			return "31-40";
		}
		if (user.getDob().getAge() <= 50) {
			return "41-50";
		}
		if (user.getDob().getAge() <= 60) {
			return "51-60";
		}
		if (user.getDob().getAge() <= 70) {
			return "61-70";
		}
		if (user.getDob().getAge() <= 80) {
			return "71-80";
		}
		if (user.getDob().getAge() <= 90) {
			return "81-90";
		}
		if (user.getDob().getAge() <= 100) {
			return "91-100";
		}
		return "100+";
	}
}
