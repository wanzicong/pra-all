package com.pra.security.config.basic;

import com.pra.security.authentication.MobileLoginFilter;
import com.pra.security.authentication.MobileLoginProvider;
import com.pra.security.filter.MobileCodeFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.annotation.Resource;


@Configuration("mobileLoginSecurityConfig")
public class MobileLoginSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Resource(name = "mobileCodeFilter")
    private MobileCodeFilter mobileCodeFilter;
    @Resource
    private UserDetailsService customUserDetailsService;
    @Resource
    private AuthenticationSuccessHandler customLoginSuccessHandler;
    @Resource
    private AuthenticationFailureHandler customLoginFailureHandler;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 获取配置文件中对手机号的配置
        String url = "/login/mobile";
        String parameter = "mobile";
        String httpMethod = "POST";

        // 登录的拦截器 Filter
        MobileLoginFilter mobileLoginFilter = new MobileLoginFilter(url, parameter, httpMethod);
        mobileLoginFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        // 设置登录拦截器 登录成功和失败的处理器 Handler
        mobileLoginFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler);
        mobileLoginFilter.setAuthenticationFailureHandler(customLoginFailureHandler);

        // 获取用户信息的 Provider
        MobileLoginProvider mobileLoginProvider = new MobileLoginProvider();
        mobileLoginProvider.setCustomUserDetailsService(customUserDetailsService);


        http.addFilterBefore(mobileCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);

        http
                /*登录拦截的用户 Filter*/.addFilterAfter(
                mobileLoginFilter,
                AbstractPreAuthenticatedProcessingFilter.class
        )/*登录的用户信息 Provider*/.authenticationProvider(mobileLoginProvider);

    }


}
