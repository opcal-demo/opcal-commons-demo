package xyz.opcal.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xyz.opcal.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private @Autowired UserService userService;

	@PostMapping("/create")
	public List<xyz.opcal.tools.response.result.User> create(@RequestParam int batch) {
		return userService.generate(batch);
	}

}
