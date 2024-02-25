package com.tawasul.web.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.tawasul.web.resource.LoginModel;
import com.tawasul.web.util.SystemConstants;

import java.io.IOException;

import javax.annotation.PostConstruct;

@ManagedBean(name = "loginManagedBean")
@RequestScoped
public class LoginManagedBean {

	private LoginModel loginModel;
	FacesMessage message = null;
	boolean loggedIn = false;

	@PostConstruct
	public void init() {
		loginModel = new LoginModel();
	}

	public void login() {

		if (getLoginModel().getUserName() != null && getLoginModel().getUserName().equals("admin")
				&& getLoginModel().getPassword() != null && getLoginModel().getPassword().equals("admin")) {
			loggedIn = true;
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", getLoginModel().getUserName());
		} else {
			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
		}

		try {
			redirectToPage(SystemConstants.ADMIN_DASHBOARD_SCREEN);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void adminLogin() {
		redirectToPage(SystemConstants.ADMIN_DASHBOARD_SCREEN);
	}

	public void redirectToPage(String page) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		try {
			externalContext.redirect(page); // Specify the target page here
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public LoginModel getLoginModel() {
		return loginModel;
	}

	public void setLoginModel(LoginModel loginModel) {
		this.loginModel = loginModel;
	}

}
