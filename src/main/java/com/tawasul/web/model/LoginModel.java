package com.tawasul.web.model;

public class LoginModel {

	private String userName;
	private String password;
	private String oneTimePassword;

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

	public String getOneTimePassword() {
		return oneTimePassword;
	}

	public void setOneTimePassword(String oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}

	@Override
	public String toString() {
		return "LoginModel [userName=" + userName + ", password=" + password + ", oneTimePassword=" + oneTimePassword
				+ "]";
	}

}
