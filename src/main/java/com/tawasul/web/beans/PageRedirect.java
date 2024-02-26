package com.tawasul.web.beans;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.tawasul.web.util.SystemConstants;

@ManagedBean(name = "pageRedirect")
@SessionScoped
public class PageRedirect {
	
	private String activeMenu = "";
	
	private final String  facesRedirect = "?faces-redirect=true";
	
	@PostConstruct
	public void init() {
		if(activeMenu.isEmpty())
			activeMenu = SystemConstants.DASHBOARD_MENU;
	}
	
	public void redirectToViewSector() {
		this.activeMenu = SystemConstants.VIEW_SECTOR_MENU;
		redirectToPage(SystemConstants.VIEW_SECTOR_SCREEN+facesRedirect);
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

	public String getActiveMenu() {
		return activeMenu;
	}

	public void setActiveMenu(String activeMenu) {
		this.activeMenu = activeMenu;
	}
	
	
}
