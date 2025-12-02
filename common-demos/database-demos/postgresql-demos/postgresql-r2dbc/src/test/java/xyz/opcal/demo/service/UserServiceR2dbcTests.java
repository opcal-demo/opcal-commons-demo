package xyz.opcal.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.test.StepVerifier;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceR2dbcTests {

	static final int DATA_SIZE = 10;

	private @Autowired UserService userService;

	@Test
	@Order(0)
	void save() {
		var generateUsers = userService.generate(DATA_SIZE);
		for (var user : generateUsers) {
			var verifierMono = userService.save(user).doOnSuccess(System.out::println);
			StepVerifier.create(verifierMono).assertNext(saved -> assertNotNull(saved.id())).verifyComplete();
		}
	}

	@Test
	@Order(1)
	void getAll() {
		var all = userService.getAll();
		var countMono = all.doOnNext(System.out::println).publish().autoConnect().count().doOnSuccess(System.out::println);
		StepVerifier.create(countMono).assertNext(total -> assertEquals(DATA_SIZE, total)).verifyComplete();
	}

}
