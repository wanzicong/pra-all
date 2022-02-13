package com.pra.securityPhone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;
import java.util.List;

/**
 * UsernamePasswordAuthenticationFilter拦截登录请求
 * UsernamePasswordAuthenticationFilter获取到用户名和密码构造一个UsernamePasswordAuthenticationToken传入AuthenticationManager
 * AuthenticationManager找到对应的Provider进行具体校验逻辑处理
 * 最后登录信息保存进SecurityContext
 *
 * @author chenyunchang
 */
@Configuration
@EnableWebSecurity
//开启Security注解(使用接口上的注解来控制访问权限)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //登录成功处理
    @Autowired
    private SuccessAuthenticationHandler successAuthenticationHandler;

    //登录失败处理
    @Autowired
    private FailureAuthenticationHandler failureAuthenticationHandler;

    //未登录处理
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    //没有权限处理
    @Autowired
    private AuthAccessDeniedHandler authAccessDeniedHandler;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Autowired
    private SmsCodeAuthenticationProvider smsCodeAuthenticationProvider;

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Http安全配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        //配置自定义登陆路径
        http.exceptionHandling()
                //未登录自定义返回
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                //没有权限访问处理
                .accessDeniedHandler(authAccessDeniedHandler)
        ;
        //短信验证码登陆验证
        //添加手机号登陆过滤器
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();

        smsCodeAuthenticationFilter.setAuthenticationManager(authenticationManager());
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);

        http.authenticationProvider(smsCodeAuthenticationProvider)
                .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(successAuthenticationHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(failureAuthenticationHandler);



        //查询所有权限,动态权限认证
        List<SysPermission> permissions = Collections.singletonList(new SysPermission("/test", "ADMIN:SELECT"));
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http
                .authorizeRequests();
        permissions.forEach(permission ->
        {
            log.info("获取权限为" + permission.getPermCode());
            //将连接地址对应的权限存入
            authorizeRequests.antMatchers(permission.getUrl()).hasAnyAuthority(permission.getPermCode());
        });
        //配置无需认证的访问路径
        http.authorizeRequests()
                // 跨域预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 登录URL
                .antMatchers("/login/**").anonymous()
                .antMatchers("/sms/**").anonymous()
                // 其他所有请求需要身份认证
                .anyRequest().authenticated();

        // 退出登录处理器
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(myLogoutSuccessHandler);
    }

    /**
     * 配置无需登陆就可以访问的路径
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/**", "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
        );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 这里要设置自定义认证
        //手机号验证
        auth.authenticationProvider(smsCodeAuthenticationProvider);
    }
}

