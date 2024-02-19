package com.tawasul.web.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.lang3.RandomStringUtils;

import com.tawasul.web.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named("userBean")
@RequestScoped
//@RequestScoped
//@ManagedBean(name = "userBean")
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user;
	private String randomPassword = "";
	private String PASSWORD_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	// ~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?"

	@PostConstruct
	public void init() {
		user = new User();
		setRandomPassword("");
	}

	public void login() {
		FacesMessage message = null;

		boolean flag = false;
		if (flag) {
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", getUser().getEmail());
		} else {
			// loggedIn = false;
			// message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error",
			// "Invalid credentials");
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		// PrimeFaces.current().ajax().addCallbackParam("loggedIn", loggedIn);
	}

	public void generatePassword() {
		String pwd = RandomStringUtils.random(9, PASSWORD_CHARACTERS);
		System.out.println("Password: " + pwd);

		setRandomPassword(pwd);
	}

	public void checkUser() {
		System.out.println("Check if user exists" + user.getEmail());
	}

	public void createUser() {
		System.out.println("Save");

	}

}
