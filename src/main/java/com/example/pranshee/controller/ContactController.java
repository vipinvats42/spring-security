package com.example.pranshee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ContactController {
	
	@GetMapping("/myContact")
	public String getContactDetails() {
		return "Here are the Contact details from the db";
	}

}
