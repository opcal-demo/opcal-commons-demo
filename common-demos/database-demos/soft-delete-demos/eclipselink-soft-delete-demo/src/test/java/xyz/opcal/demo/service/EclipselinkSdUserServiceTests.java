package xyz.opcal.demo.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EclipselinkSdUserServiceTests {

	private @Autowired SdUserService sdUserService;

	@Test
	@Order(0)
	void save() {
		assertDoesNotThrow(() -> sdUserService.generate(5).forEach(sdUserService::save));
	}

	@Test
	@Order(1)
	void deleteLast() {
		assertDoesNotThrow(() -> sdUserService.delete(sdUserService.getLastOne()));
	}

	@Test
	@Order(2)
	void lastTen() {
		var last10 = sdUserService.getLast10();
		assertFalse(CollectionUtils.isEmpty(last10));
		System.out.println(last10);
	}

}
