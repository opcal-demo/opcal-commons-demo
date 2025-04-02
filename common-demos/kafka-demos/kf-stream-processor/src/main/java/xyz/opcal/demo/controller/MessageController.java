package xyz.opcal.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xyz.opcal.demo.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {

	private @Autowired MessageService sessageService;

	@PostMapping("/create")
	public String create(@RequestParam int batch) {
		sessageService.create(batch);
		return "ok";
	}

}
