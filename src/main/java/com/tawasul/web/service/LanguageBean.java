package com.tawasul.web.service;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.tawasul.web.util.SystemConstants;
import org.apache.commons.lang3.StringUtils;

@ManagedBean(name = "languageBean")
@SessionScoped
public class LanguageBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String localeCode;

	@PostConstruct
	public void init() {
		System.out.println("Locale on load: "+ getLocaleCode() + " ---");
		if (StringUtils.isBlank(getLocaleCode()))
			setLocaleCode(SystemConstants.LOCALE_ENGLISH);
	}

	private static Map<String, Object> countries;
	static {
		countries = new LinkedHashMap<String, Object>();
		countries.put("English", Locale.ENGLISH); // label, value
		countries.put("Arabic", Locale.UNICODE_LOCALE_EXTENSION);
	}

	public Map<String, Object> getCountriesInMap() {
		return countries;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	// value change event listener
	public void countryLocaleCodeChanged(ValueChangeEvent e) {

		String newLocaleValue = e.getNewValue().toString();

		// loop country map to compare the locale code
		for (Map.Entry<String, Object> entry : countries.entrySet()) {

			if (entry.getValue().toString().equals(newLocaleValue)) {

				FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());

			}
		}
	}

	public void changeLocale() {
		System.out.println("Current Locale: " + getLocaleCode());
		if (getLocaleCode().equals(SystemConstants.LOCALE_ENGLISH)) {
			setLocaleCode(SystemConstants.LOCALE_ARABIC);
			FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("ar"));
		}
		else if (getLocaleCode().equals(SystemConstants.LOCALE_ARABIC)) {
			setLocaleCode(SystemConstants.LOCALE_ENGLISH);
			FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("en"));
		}

		System.out.println("Changed Locale: "+ getLocaleCode());
	}

}
