package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.configuration.AppInfo;

@RestController
@RequestMapping("/public")
public class PublicController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private AppInfo appInfo;
	
	@GetMapping(value = "/call")
	public ResponseEntity<String> call() {
		String activeMode = System.getProperty("spring.profiles.active");
		System.out.println("===== ===== ===== ===== : " + activeMode);
		String[] profiles = env.getActiveProfiles();
		if (profiles.length == 0) {
			profiles = env.getDefaultProfiles();
		}
		String profile = profiles[0];
		String value = profile;
		
		System.out.println("===== ===== ===== ===== : " + appInfo.getServer());
		return ResponseEntity.ok(value);
	}
	
}