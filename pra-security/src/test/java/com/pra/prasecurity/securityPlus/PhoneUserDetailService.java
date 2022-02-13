package com.pra.securityPlus;

import com.pra.securityPlus.controller.PhoneUserVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
@Service
public class PhoneUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询数据库

        // 判断用户的状态

        // 根据不同的用户状态剖出异常

        // 加载用户信息的 业务
        HashSet<String> authorities = new HashSet<>();
        authorities.add("admin:add");
        authorities.add("admin:update");
        return new PhoneUserVO("123456", username, "管理员", authorities);
    }
}
