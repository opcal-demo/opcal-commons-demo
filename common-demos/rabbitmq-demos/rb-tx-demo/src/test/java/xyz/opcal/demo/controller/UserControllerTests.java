package xyz.opcal.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;

import org.apache.commons.lang3.ArrayUtils;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import xyz.opcal.demo.service.UserService;
import xyz.opcal.tools.response.result.User;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class UserControllerTests {

	public static final String USER_CREATE_API = "/user/create?batch={batch}";

	@Autowired
	TestRestTemplate testRestTemplate;
	@Autowired
	UserService userService;

	@Test
	void create() throws NoSuchAlgorithmException {
		var batch = SecureRandom.getInstanceStrong().nextInt(1, 20);
		var response = testRestTemplate.postForEntity(USER_CREATE_API, new HttpEntity<>(""), User[].class, batch);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(ArrayUtils.isNotEmpty(response.getBody()));
		assertNotNull(response.getBody());
		assertEquals(batch, response.getBody().length);

		Awaitility.await().atMost(Duration.ofSeconds(60)).until(() -> userService.getSaveCount() >= batch);
	}

}
