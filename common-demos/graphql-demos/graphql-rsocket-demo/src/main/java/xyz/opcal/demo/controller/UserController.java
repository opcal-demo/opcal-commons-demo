package xyz.opcal.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.opcal.demo.entity.User;
import xyz.opcal.demo.service.UserService;

@Slf4j
@Controller
public class UserController {

	private @Autowired UserService userService;

	@QueryMapping
	public Mono<User> userById(@Argument Long id) {
		log.info("graphql query userById [{}]", id);
		return userService.userById(id);
	}

	@QueryMapping
	public Flux<User> fetchUsers(@Argument int pageNum, @Argument int pageSize) {
		log.info("graphql query fetchUsers pageNum [{}] pageSize [{}] ", pageNum, pageSize);
		return userService.fetchUsers(pageNum, pageSize);
	}

	@QueryMapping
	public Mono<Long> count() {
		log.info("graphql query count ");
		return userService.count();
	}

	@SubscriptionMapping
	public Flux<User> fetchAll() {
		log.info("graphql subscription fetchAll ");
		return userService.fetchAll();
	}

	@SubscriptionMapping
	public Flux<User> fetchUser(@Argument Long id) {
		log.info("graphql subscription fetchUser [{}] ", id);
		return userService.fetchUser(id);
	}
}
