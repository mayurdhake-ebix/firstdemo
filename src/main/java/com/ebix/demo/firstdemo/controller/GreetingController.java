package com.ebix.demo.firstdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebix.demo.firstdemo.service.GreetingService;

@RestController
public class GreetingController {
	
	
	private GreetingService greetingService;
	
	
	
	public GreetingController(GreetingService greetingService) {
		super();
		this.greetingService = greetingService;
	}



	@GetMapping("/greet")
    public String greet() {
        return greetingService.greet();
    }


}
