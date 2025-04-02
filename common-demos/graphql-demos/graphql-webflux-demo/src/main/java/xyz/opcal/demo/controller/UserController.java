package xyz.opcal.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.opcal.demo.entity.User;
import xyz.opcal.demo.service.UserService;

@Controller
public class UserController {

	private @Autowired UserService userService;

	@QueryMapping
	public Mono<User> userById(@Argument Long id) {
		return userService.userById(id);
	}

	@QueryMapping
	public Flux<User> fetchUsers(@Argument int pageNum, @Argument int pageSize) {
		return userService.fetchUsers(pageNum, pageSize);
	}

	@QueryMapping
	public Mono<Long> count() {
		return userService.count();
	}
}
