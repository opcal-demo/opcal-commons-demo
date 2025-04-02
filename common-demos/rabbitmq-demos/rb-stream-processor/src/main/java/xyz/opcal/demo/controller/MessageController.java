package xyz.opcal.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xyz.opcal.demo.service.MessageSourceService;
import xyz.opcal.demo.service.UserProcessService;

@RestController
@RequestMapping("/message")
public class MessageController {

	private @Autowired MessageSourceService messageSourceService;
	private @Autowired UserProcessService userProcessService;

	@PostMapping("/create")
	public String create(@RequestParam int batch) {
		messageSourceService.create(batch);
		return "ok";
	}

	@GetMapping("/age/count")
	public Map<String, Integer> ageCount() {
		return userProcessService.getAgeCounter();
	}

}
