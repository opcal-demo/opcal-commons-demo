package xyz.opcal.demo.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RedisLockService {

	private @Autowired RedisLockRegistry redisLockRegistry;

	private AtomicLong counter = new AtomicLong();

	public String get(String key) {
		final Lock lock = redisLockRegistry.obtain(key);
		try {
			lock.tryLock(30, TimeUnit.MILLISECONDS);
			log.info("Lock key: {}, count {}", key, counter.incrementAndGet());
			return UUID.fromString(key).toString();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}
	}

}
