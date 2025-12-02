package xyz.opcal.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import reactor.test.StepVerifier;
import xyz.opcal.demo.RandomuserUtils;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReactiveUserRepositoryTests {

	@Autowired
	UserRepository userRepository;

	@BeforeAll
	void init() {
		var batch = 150;
		var saveFlux = userRepository.saveAll(RandomuserUtils.generate(batch));
		var countMono = saveFlux.doOnNext(System.out::println).publish().autoConnect().count().doOnSuccess(System.out::println);
		StepVerifier.create(countMono).assertNext(total -> assertEquals(batch, total)).verifyComplete();
	}

	@Test
	@Order(0)
	void lastOne() {
		var result = userRepository.findFirstByOrderByIdDesc();
		var stepMono = result.doOnSuccess(user -> System.out.println("last user: " + user));
		StepVerifier.create(stepMono).assertNext(Assertions::assertNotNull).verifyComplete();
	}

	@Test
	@Order(1)
	void findPage() {
		var pageSize = 50;
		var result = userRepository.findBy(PageRequest.of(0, pageSize));
		var countMono = result.doOnNext(System.out::println).publish().autoConnect().count().doOnSuccess(System.out::println);
		StepVerifier.create(countMono).assertNext(total -> assertEquals(pageSize, total)).verifyComplete();
	}

}
