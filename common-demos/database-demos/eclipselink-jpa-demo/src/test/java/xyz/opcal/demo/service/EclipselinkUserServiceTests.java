package xyz.opcal.demo.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EclipselinkUserServiceTests {

	private @Autowired UserService userService;

	@Test
	void test() {
		assertDoesNotThrow(() -> userService.save(userService.generate(1).get(0)));
	}

}
