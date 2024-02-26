package com.tawasul.web.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

@Named
@ApplicationScoped
public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private static Configuration configuration;

	public HibernateUtil() {
		sessionFactory = buildSessionFactory();
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = buildSessionFactory();
		}
		return sessionFactory;
	}

	private static SessionFactory buildSessionFactory() {
		ResourceBundle rb = ResourceBundle.getBundle("hibernate");
		Properties props = new Properties();

		configuration = new Configuration();

		configuration.setProperty("hibernate.connection.url", rb.getString("hibernate.connection.url"));
		configuration.setProperty("hibernate.connection.password", rb.getString("hibernate.username"));
		configuration.setProperty("hibernate.connection.username", rb.getString("hibernate.password"));

		return configuration.configure().buildSessionFactory();
	}

}