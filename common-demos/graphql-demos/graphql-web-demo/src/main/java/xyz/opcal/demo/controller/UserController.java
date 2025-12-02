package xyz.opcal.demo.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import xyz.opcal.demo.dto.PageDTO;
import xyz.opcal.demo.dto.UserDTO;
import xyz.opcal.demo.service.UserService;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@QueryMapping
	public UserDTO userById(@Argument Long id) {
		return userService.userById(id);
	}

	@QueryMapping
	public PageDTO<UserDTO> fetchUsers(@Argument int pageNum, @Argument int pageSize) {
		return userService.fetchUsers(pageNum, pageSize);
	}
}
