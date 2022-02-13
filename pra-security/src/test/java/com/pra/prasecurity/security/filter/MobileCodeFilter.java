package com.pra.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OncePerRequestFilter 一次性的拦截器
 */
@Component("mobileCodeFilter")
public class MobileCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MobileCodeFilter.class.getName());


    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 检验 手机号码登录的验证码
        String url = "/login/mobile";

        // 获取系统对验证码检验系统自定义的配置 TODO
        String codeParamName = "code";

        // 获取发送验证码手机的设备id 作为缓存中的查询key TODO
        String deviceId = request.getHeader("deviceId");

        if (pathMatcher.match(url, request.getRequestURI())) {
            // 获取用户填写请求中的验证码
            String code = request.getParameter(codeParamName);

            if ("".equals(code)) {
                throw new RuntimeException("验证码不能为空");
            }
            // 获取发送到手机上面的验证码
            String redisCode = "6666";
            if (code.equals(redisCode)) {
                // 删除redis中的验证码缓存
                // 调用redisTemplate TODO
                filterChain.doFilter(request, response);
            } else {
                throw new RuntimeException("验证码错误请重新填写");
            }

        } else {

            // just let it go
            filterChain.doFilter(request, response);

        }

    }
}
