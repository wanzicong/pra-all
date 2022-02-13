package com.pra.securityPhone;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author chenyunchang
 * @description 自定义未登录处理
 * @date 2020-10-29 17:40:39
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HashMap<String, String> result = new HashMap<>();
        result.put("name","login失败");
        out.write(new ObjectMapper().writeValueAsString(result));
        out.flush();
        out.close();
    }
}