package xyz.opcal.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class LettucePingControllerTests {

	private @Autowired TestRestTemplate testRestTemplate;
	private @Autowired SecurityProperties securityProperties;

	@Test
	void ping() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(securityProperties.getUser().getName(), securityProperties.getUser().getPassword());
		final ResponseEntity<String> response = testRestTemplate.exchange("/ping", HttpMethod.GET, new HttpEntity<>(headers), String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
