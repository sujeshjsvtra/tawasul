package com.tawasul.web.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct; 
import javax.faces.application.FacesMessage;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.tawasul.web.service.PasswordService;

import lombok.*;

@Getter
@Setter
@RequestScoped
@Named("passwordBean")
public class PasswordBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;

	@Inject
	private PasswordService passwordService;

	@PostConstruct
	public void init() {
		passwordService = new PasswordService();

	}

	public void validate() {
		FacesMessage message = null;
		if (getEmail() != null) {

			// message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome",
			// getLoginModel().getUserName());
			// message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error",
			// "Invalid credentials");
		}

		// FacesContext.getCurrentInstance().addMessage(null, message);
		// PrimeFaces.current().ajax().addCallbackParam("loggedIn", loggedIn);
		passwordService.validateEmail(getEmail());
	}

}
