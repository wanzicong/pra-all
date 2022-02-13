package com.pra.securityPhone;

import com.pra.securityPhone.domain.PhoneUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 自定义的认证用户获取服务类
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 根据用户名获取认证用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            log.info("UserDetailsService没有接收到用户账号");
            throw new UsernameNotFoundException("UserDetailsService没有接收到用户账号");
        } else {
            // 查询数据库得到数据

            //新建权限集合
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            //模拟从数据库获取角色权限
            String[] permissions = new String[]{"ADMIN:GET","ADMIN:DELETE","ADMIN:SELECT"};
            for (String permission : permissions) {
                //封装用户信息和角色信息到SecurityContextHolder全局缓存中
                grantedAuthorities.add(new SimpleGrantedAuthority(permission));
            }
            PhoneUserDetail result = new PhoneUserDetail(UUID.randomUUID().toString(), username, "管理员", grantedAuthorities);
            //创建一个用于认证的用户对象并返回，包括：用户名，密码，角色
            HashSet<String> per = new HashSet<>(Arrays.asList(permissions));
            result.setPermissions(per);
            return result;
        }
    }
}
