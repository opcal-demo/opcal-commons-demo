package xyz.opcal.demo.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.graphql.test.autoconfigure.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import xyz.opcal.demo.dto.PageDTO;
import xyz.opcal.demo.entity.User;
import xyz.opcal.demo.repository.UserRepository;
import xyz.opcal.tools.client.RandomuserClient;
import xyz.opcal.tools.request.RandomuserRequest;
import xyz.opcal.tools.request.param.Nationalities;

@SpringBootTest
@AutoConfigureGraphQlTester
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTests {

	private @Autowired UserRepository userRepository;

	private @Autowired GraphQlTester graphQlTester;

	RandomuserClient randomuserClient = new RandomuserClient(System.getenv().getOrDefault("RANDOMUSER_API_URL", RandomuserClient.DEFAULT_API_URL));

	List<User> generate(int batch) {
		var response = randomuserClient.random(RandomuserRequest.builder().results(batch)
				.nationalities(new Nationalities[] { Nationalities.AU, Nationalities.GB, Nationalities.CA, Nationalities.US, Nationalities.NZ }).build());
		return response.getResults().stream().map(this::toUser).toList();
	}

	User toUser(xyz.opcal.tools.response.result.User result) {
		var user = new User();
		user.setFirstName(result.getName().getFirst());
		user.setLastName(result.getName().getLast());
		user.setGender(result.getGender());
		user.setAge(result.getDob().getAge());
		return user;
	}

	@BeforeAll
	void init() {
		userRepository.saveAll(generate(50));
	}

	@Test
	@Order(0)
	void userById() {
		var user = userRepository.findFirstByOrderByIdDesc().get();
		graphQlTester.documentName("userById").variable("id", user.getId()).execute().path("userById.firstName").entity(String.class)
				.isEqualTo(user.getFirstName());
	}

	@Test
	@Order(1)
	void fetchUsers() {
		var pageSize = 50;
		graphQlTester.documentName("fetchUsers").variable("pageNum", 0).variable("pageSize", pageSize).execute().path("fetchUsers").entity(PageDTO.class)
				.matches(p -> p != null && p.pageSize() == pageSize);
	}

}
