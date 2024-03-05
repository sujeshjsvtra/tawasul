package com.tawasul.web.service;

import com.tawasul.web.model.LoginModel;
import com.tawasul.web.model.User;

public interface AuthenticationService {
	
	public User login(LoginModel loginModel)throws Exception;
	
	public void saveOtp(String email,String otp);

}
