package xyz.opcal.demo.service;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RedisLockService {

	private final RedisLockRegistry redisLockRegistry;

	private final AtomicLong counter = new AtomicLong();

	public RedisLockService(RedisLockRegistry redisLockRegistry) {
		this.redisLockRegistry = redisLockRegistry;
	}

	public String get(String key) {
		var lock = redisLockRegistry.obtain(key);
		try {
			lock.lock(Duration.ofMillis(30));
			log.info("Lock key: {}, count {}", key, counter.incrementAndGet());
			return UUID.fromString(key).toString();
		} finally {
			lock.unlock();
		}
	}

}
