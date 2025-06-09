package com.example.pranshee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticesController {
	
	@GetMapping("/myNotice")
	public String getNoticeDetails() {
		return "Here are the notice details from the db";
	}

}
