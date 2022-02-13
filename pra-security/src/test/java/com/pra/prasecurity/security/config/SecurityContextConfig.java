//package com.pra.security.config;
//
//import com.pra.security.config.basic.MobileCodeSecurityConfig;
//import com.pra.security.config.basic.MobileLoginSecurityConfig;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
//
//import javax.annotation.Resource;
//
//@Configuration
//public class SecurityContextConfig extends WebSecurityConfigurerAdapter {
//
//    private static final Logger logger = LoggerFactory.getLogger(SecurityContextConfig.class.getName());
//
//    public SecurityContextConfig() {
//        logger.info("CustomSecurityConfig loading ...");
//    }
//
//    @Resource(name = "mobileCodeSecurityConfig")
//    private MobileCodeSecurityConfig mobileCodeSecurityConfig;
//    @Resource(name = "mobileLoginSecurityConfig")
//    private MobileLoginSecurityConfig mobileLoginSecurityConfig;
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic()
//                //登录认证
//                .and()
//                .authorizeRequests()
//                .antMatchers(new String[]{"/login/mobile","/login/code"})
//                .permitAll()
//                // 关闭跨站维护
//                .and()
//                .csrf()
//                .disable()
//                .apply(mobileCodeSecurityConfig) // 验证码的拦截 配置
//                .and()
//                .apply(mobileLoginSecurityConfig); // 登录用户校验拦截 配置
//
//        ExpressionUrlAuthorizationConfigurer<HttpSecurity>
//                .ExpressionInterceptUrlRegistry config = http.authorizeRequests();
//
//        config.anyRequest().authenticated();
//
//    }
//}
