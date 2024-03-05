package com.tawasul.web.beans;

import java.io.IOException;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name = "pageHeaderBean")
@SessionScoped
public class PageHeaderBean {

	String currentMenu = null;
	@PostConstruct
	public void init() {
		if (currentMenu == null)
			setCurrentMenu("home");

	}

	public void redirectToPage(String page) {
		setCurrentMenu(page);
		if (Objects.equals(page, "home")) {
			moveToPage("home");
		}else if(Objects.equals(page, "consultation")) {
			moveToPage("consultations");
		}
	}

	public void moveToPage(String page) {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
        	externalContext.redirect(page+".xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getCurrentMenu() {
		return currentMenu;
	}

	public void setCurrentMenu(String currentMenu) {
		this.currentMenu = currentMenu;
	}

}
