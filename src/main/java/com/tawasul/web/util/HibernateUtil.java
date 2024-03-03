package com.tawasul.web.util;

import java.util.ResourceBundle;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//TODO check if scope is required here
@Transactional
public class HibernateUtil {

	private static SessionFactory sessionFactory;

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

		Configuration configuration = new Configuration();

		configuration.setProperty("hibernate.connection.url", rb.getString("hibernate.connection.url"));
		configuration.setProperty("hibernate.connection.password", rb.getString("hibernate.username"));
		configuration.setProperty("hibernate.connection.username", rb.getString("hibernate.password"));

		return configuration.configure().buildSessionFactory();
	}

}