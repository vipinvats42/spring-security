package com.example.pranshee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class WelcomeController {
	
	@GetMapping("/welcome")
	public String	welcome() {
        return "Welcome to SpringApplication without security";
    }

}
