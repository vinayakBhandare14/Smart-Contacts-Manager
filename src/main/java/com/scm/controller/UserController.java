package com.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	//	user Dashbaord page 
	@GetMapping("/dashboard")
	public String userDashboard() {
		System.out.println("This is User dashBoard");
		return "user/dashboard";
	}
	
//	User profile page
	@GetMapping("/profile")
	public String userProfile() {
		System.out.println("This is User dashBoard");
		return "user/profile";
	}
//	User add contacts page
//	user view contacts page
//	user edit
//	user delete
}
