package xyz.opcal.demo.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MybatisDsqlUserServiceTests {

	@Autowired
	UserService userService;

	@Test
	@Order(0)
	void save() {
		assertDoesNotThrow(() -> userService.insert(userService.generate(1).get(0)));
	}

	@Test
	@Order(1)
	void getLastOne() {
		assertNotNull(userService.getLastOne());
	}

	@Test
	@Order(2)
	void update() {
		var user = userService.getLastOne();
		user.setLastName(user.getLastName() + "--update");
		assertDoesNotThrow(() -> userService.update(user));
	}

}
