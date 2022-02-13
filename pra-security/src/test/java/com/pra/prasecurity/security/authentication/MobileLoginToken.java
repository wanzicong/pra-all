package com.pra.security.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 *
 */
public class MobileLoginToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private static final Logger logger = LoggerFactory.getLogger(MobileLoginToken.class.getName());


    private final Object principal;

    /**
     * 构造方法 抽象类默认的
     *
     * @param principal   认证用户标识
     * @param authorities 授权列表
     */
    public MobileLoginToken(Object principal,
                            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
        logger.info("MobileLoginAuthenticationToken setAuthenticated ->true loading ...");
    }

    /**
     * 业务使用的登录 全局token
     *
     * @param mobile 手机号
     */
    public MobileLoginToken(String mobile) {
        super(null);
        this.principal = mobile;
        this.setAuthenticated(false);
        logger.info("MobileLoginAuthenticationToken setAuthenticated ->false loading ...");
    }


    @Override
    public void setAuthenticated(boolean authenticated) {
        // 刚刚创建的时间是没有被认证通过的
        if (authenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }


    @Override
    public Object getCredentials() {
        // 用户的权限集合
        return null;
    }

    @Override
    public Object getPrincipal() {
        // 用户的认证标识
        return principal;
    }
}
