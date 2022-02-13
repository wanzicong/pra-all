package com.pra.security.service;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

@Service("customLoginFailureHandler")
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

}
