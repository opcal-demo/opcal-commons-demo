package xyz.opcal.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;

import org.apache.commons.lang3.ArrayUtils;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import xyz.opcal.demo.service.UserService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserControllerTests {

	public static final String USER_CREATE_API = "/user/create?batch={batch}";

	@Autowired
	TestRestTemplate testRestTemplate;
	@Autowired
	UserService userService;

	@Test
	void create() throws NoSuchAlgorithmException {
		final int batch = SecureRandom.getInstanceStrong().nextInt(1, 20);
		final ResponseEntity<xyz.opcal.tools.response.result.User[]> response = testRestTemplate.postForEntity(USER_CREATE_API, new HttpEntity<>(null),
				xyz.opcal.tools.response.result.User[].class, batch);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(ArrayUtils.isNotEmpty(response.getBody()));
		assertEquals(batch, response.getBody().length);

		Awaitility.await().atMost(Duration.ofSeconds(60)).until(() -> userService.getSaveCount() >= batch);
	}

}
