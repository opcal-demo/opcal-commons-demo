package xyz.opcal.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import xyz.opcal.demo.RandomuserUtils;
import xyz.opcal.demo.entity.User;

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
		final Mono<Long> countMono = saveFlux.doOnNext(System.out::println).publish().autoConnect().count().doOnSuccess(System.out::println);
		StepVerifier.create(countMono).assertNext(total -> assertEquals(batch, total)).verifyComplete();
	}

	@Test
	@Order(0)
	void lastOne() {
		Mono<User> result = userRepository.findFirstByOrderByIdDesc();
		final Mono<User> stepMono = result.doOnSuccess(user -> System.out.println("last user: " + user));
		StepVerifier.create(stepMono).assertNext(user -> assertNotNull(user)).verifyComplete();
	}

	@Test
	@Order(1)
	void findPage() {
		final var pageSize = 50;
		Flux<User> result = userRepository.findBy(PageRequest.of(0, pageSize));
		final Mono<Long> countMono = result.doOnNext(System.out::println).publish().autoConnect().count().doOnSuccess(System.out::println);
		StepVerifier.create(countMono).assertNext(total -> assertEquals(pageSize, total)).verifyComplete();
	}

}
