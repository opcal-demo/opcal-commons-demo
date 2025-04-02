package xyz.opcal.demo.function;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import xyz.opcal.demo.model.User;
import xyz.opcal.demo.service.UserService;

@Configuration
public class UserFunction {

	private final UserService userService;

	public UserFunction(UserService userService) {
		this.userService = userService;
	}

	@Bean
	public Consumer<Integer> generate() {
		return userService::generate;
	}

	@Bean
	public Supplier<List<User>> findAll() {
		return userService::findAll;
	}

}
