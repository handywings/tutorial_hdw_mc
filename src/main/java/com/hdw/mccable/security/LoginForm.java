package com.hdw.mccable.security;

public class LoginForm {
	private String username;
	private String password;
	private String accessToken;
	private String personnelName;
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
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getPersonnelName() {
		return personnelName;
	}
	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}
	@Override
	public String toString() {
		return "LoginForm [username=" + username + ", password=" + password + ", accessToken=" + accessToken
				+ ", personnelName=" + personnelName + "]";
	}
	
}
