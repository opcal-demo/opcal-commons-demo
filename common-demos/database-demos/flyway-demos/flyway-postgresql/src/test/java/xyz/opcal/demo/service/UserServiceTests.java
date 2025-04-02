package xyz.opcal.demo.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;
import xyz.opcal.demo.entity.User;

@Slf4j
@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class UserServiceTests {

	static final int DATA_SIZE = 10;

	private @Autowired UserService userService;
	private @Autowired JdbcTemplate jdbcTemplate;

	@Test
	@Order(0)
	void save() {
		assertDoesNotThrow(() -> userService.generate(DATA_SIZE).forEach(userService::save));
	}

	@Test
	@Order(1)
	void getAll() {
		final List<User> all = userService.getAll();
		assertEquals(DATA_SIZE, all.size());
		log.info("{}", all);
	}

	@Test
	@Order(1)
	void checkHistory() {
		final List<Map<String, Object>> results = jdbcTemplate.queryForList("SELECT * FROM flyway_schema_history");
		assertFalse(CollectionUtils.isEmpty(results));
		log.info("history results: \n{}", results);
	}

}
