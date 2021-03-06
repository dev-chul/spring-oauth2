package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class RestResourceController {
	private static final Logger log = LoggerFactory.getLogger(RestResourceController.class);

	@RequestMapping(value="/users", method=RequestMethod.GET)
	@ResponseBody
	public String  profile() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		log.debug("Principal : {}", principal);
		
		String username = "";
		String email = "";
		if(principal instanceof User) {
			User user = (User) principal;
			username = user.getUsername();
			email = user.getUsername() + "@email.com";
		} else {
			username = (String) principal;
			email = username + "@email.com";
		}
		
		UserProfile profile = new UserProfile();
		profile.setName(username);
		profile.setEmail(email);

		log.debug("/api/users : {}", profile);
		
		return profile.toJson();
	}
}

class UserProfile {
	private String name;
	private String email;

	// Setters and getters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserProfile [name=" + name + ", email=" + email + "]";
	}

	public String toJson() {
		return String.format("{\"name\" : \"%s\", \"email\" : \"%s\", }", name, email);
	}
}
