package xyz.opcal.demo.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RedissonService {

	private @Autowired RedissonClient redisson;

	private AtomicLong counter = new AtomicLong();

	public String get(String key) {
		final RLock fairLock = redisson.getFairLock("redisson-lock" + key);
		try {
			fairLock.tryLock(30, 30, TimeUnit.MILLISECONDS);
			log.info("Lock key: {}, count {}", key, counter.incrementAndGet());
			return UUID.fromString(key).toString();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			fairLock.unlock();
		}
	}

}
