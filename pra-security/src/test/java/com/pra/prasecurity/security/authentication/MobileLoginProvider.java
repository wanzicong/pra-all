package com.pra.security.authentication;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class MobileLoginProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(MobileLoginProvider.class.getName());


    @Getter
    @Setter
    // 实现这个接口 自定义数据库查询  在配置类中会自动填充进来
    private UserDetailsService customUserDetailsService;

    /**
     * 构造器
     */
    public MobileLoginProvider() {
        logger.info("MobileLoginAuthenticationProvider loading ...");
    }


    /**
     * 登录认证
     *
     * @param authentication 自定义的用户登录token
     * @return 返回
     * @throws AuthenticationException 认证异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取自定义的全局token 信息
        MobileLoginToken mobileLoginToken = (MobileLoginToken) authentication;

        //获取用户信息（数据库认证）
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(
                /*手机号*/(String) mobileLoginToken.getPrincipal()
        );

        // 判断这个用户是不是存在这个系统中
        if (userDetails == null) {
            logger.info("系统中不存在该用户" +mobileLoginToken.getPrincipal());
            throw new InternalAuthenticationServiceException("Unable to obtain user information");
        }

        // ======================== 构建全局的 auth对象 ===========================

        // 验证通过 调用验证通过的token构造方法
        MobileLoginToken result = new MobileLoginToken(
                userDetails,
                userDetails.getAuthorities()
        );
        result.setDetails(mobileLoginToken.getDetails());

        return result;
    }

    /**
     * 根据token类型，来判断使用哪个Provider
     */
    @Override
    public boolean supports(Class<?> authentication) {
        // 手机号码登录的校验
        return MobileLoginToken.class.isAssignableFrom(authentication);
    }
}
