package xyz.opcal.demo.service;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	Logger consumerLogger = LoggerFactory.getLogger("consumer");

	private final AtomicLong messageCounter = new AtomicLong();

	public long consumeCount() {
		return messageCounter.get();
	}

	/**
	 * mock another group consumer
	 * 
	 * @param message
	 */
	@KafkaListener(topics = "kf_new_user_age_count", groupId = "${spring.application.name}")
	public void ageCount(ConsumerRecord<byte[], byte[]> message) {
		consumerLogger.info("**** [{}]={}", new String(message.key()), new BigInteger(message.value()));
		messageCounter.incrementAndGet();
	}
}
