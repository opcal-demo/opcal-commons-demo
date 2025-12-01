package xyz.opcal.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.security.autoconfigure.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class PingControllerTests {

	private @Autowired TestRestTemplate testRestTemplate;
	private @Autowired SecurityProperties securityProperties;

	@Test
	void ping() {
		var headers = new HttpHeaders();
		headers.setBasicAuth(securityProperties.getUser().getName(), securityProperties.getUser().getPassword());
		var response = testRestTemplate.exchange("/ping", HttpMethod.GET, new HttpEntity<>(headers), String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
