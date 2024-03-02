package com.tawasul.web.beans;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.StringUtils;

import com.tawasul.web.model.LoginModel;
import com.tawasul.web.model.User;
import com.tawasul.web.service.impl.AuthenticationServiceImpl;
import com.tawasul.web.util.MessageUtil;

@ManagedBean(name = "loginManagedBean")
@SessionScoped
public class LoginManagedBean {
	 private boolean showOverlay = false;
	private LoginModel loginModel;
	
	@ManagedProperty(value="#{authenticationServiceImpl}")
	private AuthenticationServiceImpl authenticationServiceImpl;

	@PostConstruct
	public void init() {
		loginModel = new LoginModel();
		 
	}

 

	public String adminLogin() {
		if (validateUser()) {
			try {
				 User user = authenticationServiceImpl.login(getLoginModel());
				 if(user!=null) {
					  showOverlay = false;
					 return "admin-dashboard?faces-redirect=true";
				 }
				 MessageUtil.info(" Invalid credentials");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			
		return null;
	}

	private boolean validateUser() {
		boolean result = true;
		FacesMessage message = null;
		FacesContext context = FacesContext.getCurrentInstance();
		if (StringUtils.isEmpty(getLoginModel().getUserName())) {
			MessageUtil.info(" Email cannot be an empty");
			result = false;
		}else if(!isValidEmail()) {
			MessageUtil.error(" Invalid email address");
			result = false;
		}else if(StringUtils.isEmpty(getLoginModel().getPassword())) {
			MessageUtil.info(" Password cannot be an empty");
			result = false; 
		}

//		context.addMessage(null, message);
		return result;
	}
 
	
	private boolean isValidEmail() {
		boolean result = true;
		try {
		InternetAddress emailAddr = new InternetAddress(getLoginModel().getUserName());
		emailAddr.validate();
		} catch (AddressException ex) {
		result = false;
		}
		return result;
	}

	public LoginModel getLoginModel() {
		return loginModel;
	}

	public void setLoginModel(LoginModel loginModel) {
		this.loginModel = loginModel;
	}



	public AuthenticationServiceImpl getAuthenticationServiceImpl() {
		return authenticationServiceImpl;
	}



	public void setAuthenticationServiceImpl(AuthenticationServiceImpl authenticationServiceImpl) {
		this.authenticationServiceImpl = authenticationServiceImpl;
	}



	public boolean isShowOverlay() {
		return showOverlay;
	}



	public void setShowOverlay(boolean showOverlay) {
		this.showOverlay = showOverlay;
	}
	
	

}
