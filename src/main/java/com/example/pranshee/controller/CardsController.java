package com.example.pranshee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {
	
	@GetMapping("/myCards")
	public String getCardsDetails() {
		return "Here are the Cards details from the db";
	}

}
