package xyz.opcal.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import xyz.opcal.demo.RandomuserUtils;
import xyz.opcal.demo.entity.User;
import xyz.opcal.demo.repository.UserRepository;

@SpringBootTest
@AutoConfigureGraphQlTester
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RSocketUserControllerTests {

	private @Autowired UserRepository userRepository;

	private @Autowired GraphQlTester graphQlTester;

	User user;

	@BeforeAll

	void init() {
		var batch = 100;
		var saveFlux = userRepository.saveAll(RandomuserUtils.generate(batch));
		final Mono<Long> countMono = saveFlux.doOnNext(System.out::println).publish().autoConnect().count().doOnSuccess(System.out::println);
		StepVerifier.create(countMono).assertNext(total -> assertEquals(batch, total)).verifyComplete();
		user = userRepository.findFirstByOrderByIdDesc().doOnNext(user -> assertNotNull(user)).doOnSuccess(System.out::println).block();
	}

	@Test
	@Order(0)
	void userById() {
		graphQlTester.documentName("userById").variable("id", user.id()).execute().path("userById.firstName").entity(String.class).isEqualTo(user.firstName());
	}

	@Test
	@Order(1)
	void fetchUsers() {
		var pageSize = 50;
		graphQlTester.documentName("fetchUsers").variable("pageNum", 0).variable("pageSize", pageSize).execute().path("fetchUsers").entity(User[].class)
				.matches(p -> p.length == pageSize).satisfies(p -> System.out.println(Arrays.toString(p)));
	}

	@Test
	@Order(2)
	void count() {
		graphQlTester.documentName("count").execute().path("count").entity(Long.class).matches(p -> p != 0).satisfies(System.out::println);
	}

	@Test
	@Order(4)
	void subscriptionVerifyAllData() throws InterruptedException {
		var total = graphQlTester.documentName("count").execute().path("count").entity(Long.class).get();
		StepVerifier.create(this.graphQlTester.documentName("fetchAll").executeSubscription().toFlux()) //
				.consumeNextWith(response -> response.path("fetchAll").hasValue())//
				.expectNextCount(total - 1) //
				.verifyComplete();
	}

	@Test
	@Order(5)
	void subscriptionFetchUser() {
		var result = this.graphQlTester.documentName("fetchUser").variable("id", user.id()).executeSubscription().toFlux();

		StepVerifier.create(result) //
				.consumeNextWith(response -> response.path("fetchUser").hasValue())//
				.verifyComplete();
	}

}
