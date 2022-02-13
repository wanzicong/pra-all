package com.pra.securityPlus;

import com.pra.securityPlus.controller.PhoneUserVO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PhoneJwtTokenFilter extends OncePerRequestFilter {

    @Resource
    private PhoneLoginService phoneLoginService;

    @Override
    @SuppressWarnings("all")
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 验证token的拦截器
        // TODO
        PhoneUserVO phoneUserVO = phoneLoginService.getUserInfo(request);

        // 验证jwt token 刷新即将到期的token

        // 创建验证成功的token
        PhoneAuthenticationToken phoneAuthenticationToken = new PhoneAuthenticationToken(
                phoneUserVO.getAuthorities(),
                phoneUserVO
        );
        phoneAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(phoneAuthenticationToken);
    }
}
