package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {
	
	@GetMapping(value = "/call")
	public ResponseEntity<String> call() {
		String value = "called!!";
		return ResponseEntity.ok(value);
	}
	
}