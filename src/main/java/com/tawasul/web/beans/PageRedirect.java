package com.tawasul.web.beans;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean; 
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.tawasul.web.util.SystemConstants;

@Named("pageRedirect")
@SessionScoped
public class PageRedirect {

	private String activeMenu = "";
	private String defaultLabel = SystemConstants.LABEL_DASHBOARD;
	private String activeLabel = "";

	private final String facesRedirect = "?faces-redirect=true";

	@PostConstruct
	public void init() {
		if (activeMenu.isEmpty())
			activeMenu = SystemConstants.DASHBOARD_MENU;
		defaultLabel = SystemConstants.LABEL_DASHBOARD;
	}

	public void redirectFromRemoteCommand() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		String menu = (String) map.get("menu");
		switch (menu) {
		case SystemConstants.DASHBOARD_MENU:
			redirectToPage(SystemConstants.ADMIN_DASHBOARD_SCREEN + facesRedirect);
			setActiveMenu(SystemConstants.DASHBOARD_MENU);
			setActiveLabel("");
			break;
		case SystemConstants.VIEW_SECTOR_MENU:
			redirectToPage(SystemConstants.VIEW_SECTOR_SCREEN + facesRedirect);
			setActiveMenu(SystemConstants.COMMON_SECTOR_MENU);
			setActiveLabel(SystemConstants.LABEL_VIEW_SECTOR);
			break;
		case SystemConstants.ADD_SECTOR_MENU:
			redirectToPage(SystemConstants.ADD_SECTOR_SCREEN + facesRedirect);
			setActiveMenu(SystemConstants.COMMON_SECTOR_MENU);
			setActiveLabel(SystemConstants.LABEL_ADD_SECTOR);
			break;
		case SystemConstants.ADD_CONSULTATIONS_MENU:
			redirectToPage(SystemConstants.ADD_CONSULTATIONS_SCREEN + facesRedirect);
			setActiveMenu(SystemConstants.COMMON_CONSULTATIONS_MENU);
			setActiveLabel(SystemConstants.LABEL_ADD_CONSULTATIONS);
			break;
		case SystemConstants.VIEW_CONSULTATIONS_MENU:
			redirectToPage(SystemConstants.VIEW_CONSULTATIONS_SCREEN + facesRedirect);
			setActiveMenu(SystemConstants.COMMON_CONSULTATIONS_MENU);
			setActiveLabel(SystemConstants.LABEL_VIEW_CONSULTATIONS);
			break;
		case SystemConstants.VIEW_INITIATIVES_MENU:
			redirectToPage(SystemConstants.VIEW_INITIATIVES_SCREEN + facesRedirect);
			setActiveMenu(SystemConstants.COMMON_INITIATIVES_MENU);
			setActiveLabel(SystemConstants.LABEL_VIEW_INITIATIVES);
			break;
		}
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

	public String getDefaultLabel() {
		return defaultLabel;
	}

	public void setDefaultLabel(String defaultLabel) {
		this.defaultLabel = defaultLabel;
	}

	public String getActiveLabel() {
		return activeLabel;
	}

	public void setActiveLabel(String activeLabel) {
		this.activeLabel = activeLabel;
	}

}
