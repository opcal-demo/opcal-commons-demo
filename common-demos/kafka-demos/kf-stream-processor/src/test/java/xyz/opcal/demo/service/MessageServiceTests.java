package xyz.opcal.demo.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.Duration;
import java.util.Random;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessageServiceTests {

	@Autowired
	MessageService messageService;

	@Autowired
	UserService userService;

	Random random = new Random();

	@Test
	@Order(0)
	void sendMessage() {
		var batch = random.nextInt(10, 50);
		assertDoesNotThrow(() -> messageService.create(batch));
		log.info("$$$$$ created message size {}", batch);
	}

	@Test
	@Order(1)
	void consume() {
		Awaitility.await().atMost(Duration.ofMinutes(5)).until(() -> userService.consumeCount() > 0);
	}

}
