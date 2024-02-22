package com.tawasul.web.util;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileNotFoundException;
import java.io.FileReader;

@WebListener
public class InitializerListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		initializeData();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	private void initializeData() {

	}

}