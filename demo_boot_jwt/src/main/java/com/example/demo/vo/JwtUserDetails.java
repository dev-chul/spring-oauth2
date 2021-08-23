package com.example.demo.vo;

import java.io.Serializable;

public class JwtUserDetails implements Serializable{

	private static final long serialVersionUID = -4974077550971129127L;
	
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "JwtUserDetails [username=" + username + ", password=" + password + "]";
	}
	
}
