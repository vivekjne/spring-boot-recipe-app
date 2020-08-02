package com.recipe.ws.security;

import com.recipe.ws.SpringApplicationContext;


public class SecurityConstants {

	public static final long EXPIRATION_TIME = 864000000;
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users";
	public static final String LOGIN_URL = "/users/login";
	public static final String VERIFICATION_EMAIL_URL = "/users/email-verification";
	public static final String SWAGGER_UI_URL = "/swagger-ui.html#";
	
	public static String getTokenSecret() {
		AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
		return appProperties.getTokenSecret();
	}
}
