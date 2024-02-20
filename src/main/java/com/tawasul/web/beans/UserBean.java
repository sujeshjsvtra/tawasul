package com.tawasul.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;

import com.tawasul.web.model.User;
import com.tawasul.web.util.HibernateUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Named("userBean")
@RequestScoped
@NoArgsConstructor(force = true)
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user;
	private String randomPassword = "";
	private String PASSWORD_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	@Inject
	private Session session;

	@Inject
	private HibernateUtil hibernateUtil;

	@PostConstruct
	public void init() {
		user = new User();
		setRandomPassword("");
		session = hibernateUtil.getSessionFactory().openSession();
	}

	public void createUser() {
		session.beginTransaction();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		criteria.from(User.class);
		List<User> users = session.createQuery(criteria).getResultList();

		System.out.println("Size of list is " + users.size());
		session.close();
	}

	public void generatePassword() {
		String pwd = RandomStringUtils.random(9, PASSWORD_CHARACTERS);
		System.out.println("Password: " + pwd);

		setRandomPassword(pwd);
	}

	public void checkUser() {
	}

}
