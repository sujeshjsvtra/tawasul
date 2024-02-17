package com.tawasul.web.service;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

@Named
@ApplicationScoped
@Getter
@Setter
public class PasswordService {

	@PostConstruct
	public void init() {
		System.out.println("PasswordService: " + LocalDateTime.now());
		otherMethod();
	}

	public void validateEmail(String email) {
		System.out.println("Password service: " + email);
		// 1. Validate if email format is correct
		// 2. Check if user exists
		// 3. If so, retrieve user and send email

	}

	public void otherMethod() {
		System.out.println("otherMethod");
	}
}
