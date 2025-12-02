package xyz.opcal.demo.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RedisLockService {

	private final RedisLockRegistry redisLockRegistry;

	private final AtomicLong tryCounter = new AtomicLong();
	private final AtomicLong counter = new AtomicLong();

	public RedisLockService(RedisLockRegistry redisLockRegistry) {
		this.redisLockRegistry = redisLockRegistry;
	}

	public String get(String key) {
		var lock = redisLockRegistry.obtain(key);
		var isLock = false;
		try {
			var tryNum = tryCounter.incrementAndGet();
			log.info("try Lock key: {}, count {}", key, tryNum);
			isLock = lock.tryLock(10, TimeUnit.MILLISECONDS);
			if (isLock) {
				log.info("Lock key: {}, try {} count {}", key, tryNum, counter.incrementAndGet());
			}
			return UUID.fromString(key).toString();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			if (isLock) {
				lock.unlock();
			}
		}
	}

}
