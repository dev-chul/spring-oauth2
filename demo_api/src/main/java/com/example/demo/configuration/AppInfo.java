package com.example.demo.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.resource")
public class AppInfo {
	
	private String server;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

}
