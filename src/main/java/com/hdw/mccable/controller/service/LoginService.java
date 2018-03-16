package com.hdw.mccable.controller.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hdw.mccable.entity.Authentication;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.security.LoginForm;
import com.hdw.mccable.security.TokenUtil;
import com.hdw.mccable.service.AuthenService;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping(value = "/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginService {

	private static final Logger log = LoggerFactory.getLogger(LoginService.class);
	static final String AUDIENCE_MOBILE = "mobile";
	
	@Autowired(required = true)
	@Qualifier(value = "authenService")
	private AuthenService authenService;
	
	@RequestMapping(value = "/authentication", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginForm> authentication(@RequestBody LoginForm loginForm) throws Exception {
		log.info("loginForm : {}",loginForm);
		try {		
			Authentication authen = authenService.findByUserNamePassword(loginForm);
			if(authen != null){
				Personnel personnel = authen.getPersonnel();
				if(null != personnel){
					String accessToken = TokenUtil.generateToken(""+personnel.getId(), AUDIENCE_MOBILE);
					loginForm.setAccessToken(accessToken);
					loginForm.setPersonnelName(personnel.getPrefix()+personnel.getFirstName()+" "+personnel.getLastName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return new ResponseEntity<LoginForm>(loginForm, HttpStatus.OK);
	}
	
}
