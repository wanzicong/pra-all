package com.pra.security.authentication;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MobileLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Getter
    private final boolean postOnly = true;
    private static final Logger logger = LoggerFactory.getLogger(MobileLoginFilter.class.getName());


    @Getter
    @Setter
    // 手机号参数名字
    private String mobileParameterName;


    /**
     * 构造器
     *
     * @param mobileLoginUrl      登录的url
     * @param mobileParameterName 手机号在request 中的差数名
     * @param httpMethod          登录请求的方法
     */
    public MobileLoginFilter(String mobileLoginUrl,
                             String mobileParameterName,
                             String httpMethod) {
        // 路径请求匹配器
        // 指定这个拦截器 拦截的请求url 和 方法
        super(new AntPathRequestMatcher(mobileLoginUrl, httpMethod));
        // 手机号参数名字
        this.mobileParameterName = mobileParameterName;
        logger.info("MobileLoginAuthenticationFilter loading ...");
    }


    /**
     * 身份验证
     *
     * @param request  请求
     * @param response 相应
     * @return 返回 return
     * @throws AuthenticationException 异常
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        // 限制提交的请求是post请求
        if (postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        // 获取手机号码
        String phone = this.getMobileByRequest(request);

        // 构建没有经过认证的token
        MobileLoginToken authToken = new MobileLoginToken(phone);

        // 设置token中的详细信息
        this.setUserTokenDetails(request, authToken);

        // 根据构建的全局token信息进行详细的校验 (登录校验 权限校验......)
        return this.getAuthenticationManager().authenticate(authToken);
    }

    /**
     * 设置用户的详细信息 (id 角色 权限)
     *
     * @param request   请求对象
     * @param authToken 全局的token
     */
    private void setUserTokenDetails(HttpServletRequest request,
                                     MobileLoginToken authToken) {
        authToken.setDetails(
                // 自定义一个查询用户信息的service 和 provider
                authenticationDetailsSource.buildDetails(request)
        );
    }

    /**
     * 根据请求对象获取请求中的手机号
     *
     * @param request 请求对象
     * @return 返回手机号
     */
    private String getMobileByRequest(HttpServletRequest request) {
        return request.getParameter(mobileParameterName);
    }
}
