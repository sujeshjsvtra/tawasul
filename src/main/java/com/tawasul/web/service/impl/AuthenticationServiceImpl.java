package com.tawasul.web.service.impl;

import java.time.ZonedDateTime;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import com.tawasul.web.model.LoginModel;
import com.tawasul.web.model.PasswordResetToken;
import com.tawasul.web.model.User;
import com.tawasul.web.service.AuthenticationService;
import com.tawasul.web.util.DBUtil;
import com.tawasul.web.util.SystemConstants;

@ManagedBean(name = "authenticationServiceImpl")
@SessionScoped
public class AuthenticationServiceImpl  implements AuthenticationService{
 
	
	@PostConstruct
	public void init() {
		 
	}
	
	@Override
	public User login(LoginModel loginModel) throws Exception {
		try {
			User user = DBUtil.getUserByEmail(loginModel.getUserName());
			if(user!=null) {
				if(Objects.equals(loginModel.getPassword(), user.getPassword())) {
					return user;
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public void saveOtp(String email, String otp) {
		PasswordResetToken restPassword = new PasswordResetToken();
		restPassword.setEmail(email);
		restPassword.setOtp(otp);
		restPassword.setCreatedAt(ZonedDateTime.now());
		restPassword.setExpiredAt( (ZonedDateTime.now()).plusMinutes(30) );
		restPassword.setIsDeleted(SystemConstants.ACTIVE);
		
		DBUtil.saveOrUpdate(restPassword);
		
	}

}
