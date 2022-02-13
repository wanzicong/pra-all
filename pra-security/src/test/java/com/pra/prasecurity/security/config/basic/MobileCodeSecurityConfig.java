package com.pra.security.config.basic;

import com.pra.security.filter.MobileCodeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.annotation.Resource;

@Configuration("mobileCodeSecurityConfig")
public class MobileCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private static final Logger logger = LoggerFactory.getLogger(MobileCodeSecurityConfig.class.getName());

    public MobileCodeSecurityConfig() {
        logger.info("ValidateCodeSecurityConfig loading.....");
    }


    @Resource(name = "mobileCodeFilter")
    private MobileCodeFilter mobileCodeFilter;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 设置验证号码 校验的拦截器
        http.addFilterBefore(mobileCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }
}
