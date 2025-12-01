package xyz.opcal.demo.function;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestRestTemplate
class UserFunctionTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	@Order(0)
	void generate() {
		var response = restTemplate.postForEntity("/generate", 10, String.class);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		log.info("[/generate] result [{}] ", response.getBody());
	}

	@Test
	@Order(1)
	void findAll() {
		var response = restTemplate.getForEntity("/findAll", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		log.info("[/findAll] result [{}] ", response.getBody());
	}

}
