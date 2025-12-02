package xyz.opcal.demo.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class UserServiceTests {

	static final int DATA_SIZE = 10;

	private @Autowired UserService userService;

	@Test
	@Order(0)
	void save() {
		assertDoesNotThrow(() -> userService.generate(DATA_SIZE).forEach(userService::save));
	}

	@Test
	@Order(1)
	void getAll() {
		var all = userService.getAll();
		log.info("all users {}", all);
		assertEquals(DATA_SIZE, all.size());
	}

}
