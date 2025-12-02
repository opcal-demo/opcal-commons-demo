package xyz.opcal.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xyz.opcal.demo.service.MessageSourceService;

@RestController
@RequestMapping("/message")
public class MessageController {

	private final MessageSourceService messageSourceService;

	public MessageController(MessageSourceService messageSourceService) {
		this.messageSourceService = messageSourceService;
	}

	@PostMapping("/create")
	public String create(@RequestParam int batch) {
		messageSourceService.create(batch);
		return "ok";
	}

}
