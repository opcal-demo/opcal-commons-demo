package xyz.opcal.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestRestTemplate
class MessageProcessServiceTests {

	static final String CREATE_URL = "/message/create?batch={batch}";

	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	UserProcessService userProcessService;

	Random random = new Random();

	@Test
	@Order(0)
	void create() {
		Map<String, Object> urlVariables = new HashMap<>();
		var batch = random.nextInt(10, 50);
		urlVariables.put("batch", batch);
		var response = restTemplate.postForEntity(CREATE_URL, null, String.class, urlVariables);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		log.info("$$$$$$ created message size {}", batch);
	}

	@Test
	@Order(1)
	void ageCount() {
		Awaitility.await().atMost(Duration.ofSeconds(90)).until(() -> userProcessService.consumeCount() > 0);
	}

}
