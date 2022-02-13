package com.pra.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service("customUserDetailsService")
public class MobileUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 加载数据库

        // 查询用户的基本信息

        // 查询用户的
        return new MobileUser("phone", "",
                true,
                true,
                true,
                true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));

    }

    static class MobileUser extends User {
        // 可以自定义 属性 比如用户的 管理员id
        /**
         * 管理员id
         */
        private String adminId;

        public MobileUser(String username,
                          String password,
                          Collection<? extends GrantedAuthority> authorities) {
            super(username, password, authorities);
        }

        public MobileUser(String username,
                          String password,
                          boolean enabled,
                          boolean accountNonExpired,
                          boolean credentialsNonExpired,
                          boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
            super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        }
    }

}
