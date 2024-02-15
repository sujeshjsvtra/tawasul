package com.tawasul.web.resource;

public class LoginModel {

	private String userName;
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginModel(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public LoginModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "LoginModel [userName=" + userName + ", password=" + password + "]";
	}

}
