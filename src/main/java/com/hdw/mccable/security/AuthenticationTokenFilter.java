package com.hdw.mccable.security;



import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationTokenFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationTokenFilter.class);
    
	final static String tokenHeader = "Authorization";
	
    public static String validateToken(HttpServletRequest request) {
    	 final String authToken = request.getHeader(tokenHeader);
         String userId = null;
         logger.info("authToken : " + authToken);
         if (authToken != null) {
//             logger.info("authToken7 : " + authToken);
             try {
            	 userId = TokenUtil.getUsernameFromToken(authToken);
                 logger.info("username : {}",userId);
             } catch (IllegalArgumentException e) {
                 logger.error("an error occured during getting username from token", e);
                 return null;
             }
         } else {
             logger.warn("couldn't find bearer string, will ignore the header");
             return null;
         }
    	 return userId;
    }
}
