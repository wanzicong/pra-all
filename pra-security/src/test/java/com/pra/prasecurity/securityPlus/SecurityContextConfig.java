package com.pra.securityPlus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import javax.annotation.Resource;

/**
 * 全局 security 配置
 */
@Configuration
public class SecurityContextConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PhoneJwtTokenFilter phoneJwtTokenFilter(){
        return new PhoneJwtTokenFilter();
    }


    @Resource
    private PhoneUserDetailService phoneUserDetailService;
    @Resource
    private UnAuthorizedHandler unauthorizedHandler;
    @Resource
    private UnAccessDeniedHandler unAccessDeniedHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // 用户认证管理器
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 加载用户详情的service
        auth.userDetailsService(phoneUserDetailService);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                // CSRF禁用，因为不使用session
                .csrf().disable()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 对于登录login 注册register 验证码captchaImage 允许匿名访问
                .antMatchers("/wan/login").anonymous()
                .antMatchers("/wan/test").anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();

        // 添加JWT filter
        httpSecurity.addFilterBefore(phoneJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }


}
