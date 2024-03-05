package com.tawasul.web.util;

import org.primefaces.PrimeFaces;

public class DialogueUtil {
	public static void showDialog(String widgetVar) {
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('"+widgetVar+"').show()");
	}
	
	public static void hideDialog(String widgetVar) {
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('"+widgetVar+"').show()");
	}
}
