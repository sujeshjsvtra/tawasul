package com.tawasul.web.service.impl;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import com.tawasul.web.model.LoginModel;
import com.tawasul.web.model.User;
import com.tawasul.web.service.AuthenticationService;
import com.tawasul.web.util.DBUtil;

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

}
