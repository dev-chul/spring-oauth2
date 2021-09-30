package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.example.demo.configuration.AppInfo;

@SpringBootApplication
@EnableConfigurationProperties(AppInfo.class)
public class DemoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
