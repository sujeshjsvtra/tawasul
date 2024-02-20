package com.tawasul.web.resource;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

@ManagedBean(name = "loginManagedBean")
@RequestScoped
public class LoginManagedBean {

	private LoginModel loginModel;

	@PostConstruct
	public void init() {
		loginModel = new LoginModel();
	}
	
	public void login() {
        FacesMessage message = null;
        boolean loggedIn = false;
         
        if(getLoginModel().getUserName() != null && getLoginModel().getUserName().equals("admin") && getLoginModel().getPassword() != null && getLoginModel().getPassword().equals("admin")) {
            loggedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", getLoginModel().getUserName());
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        }
         
        // FacesContext.getCurrentInstance().addMessage(null, message);
        // PrimeFaces.current().ajax().addCallbackParam("loggedIn", loggedIn);

        redirectToPage("admin/admin-dashboard.xhtml");
    }  
	
    public void redirectToPage(String pageUrl) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		try {
			externalContext.redirect(pageUrl); // Specify the target page here
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
