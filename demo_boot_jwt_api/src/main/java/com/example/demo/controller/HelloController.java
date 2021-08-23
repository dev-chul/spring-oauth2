package com.example.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/hello")
    public String firstPage() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = (User) principal;
		String username = user.getUsername();
		String email = user.getUsername() + "@email.com";
		
		UserProfile profile = new UserProfile();
		profile.setName(username);
		profile.setEmail(email);

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