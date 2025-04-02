package xyz.opcal.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class LettuceRedisLockServiceTests {

	@Autowired
	RedisLockService redisLockService;

	ExecutorService executorService;

	@BeforeAll
	void init() {
		executorService = Executors.newVirtualThreadPerTaskExecutor();
	}

	@AfterAll
	void stop() {
		executorService.shutdown();
	}

	@Test
	void concurrent() throws InterruptedException {

		final String key = UUID.randomUUID().toString();

		for (int i = 0; i < 1000; i++) {
			executorService.submit(() -> assertEquals(key, redisLockService.get(key)));
		}

		executorService.shutdown();
		var count = 0;
		while (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
			System.out.println("Waiting " + count);
			count++;
		}
	}

}
