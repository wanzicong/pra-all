package com.pra.securityPlus.controller;

import cn.hutool.http.server.HttpServerResponse;
import com.pra.securityPlus.PhoneLoginService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/wan")
public class SecurityController {

    @Resource
    private PhoneLoginService phoneLoginService;

    @GetMapping("/test")
    public Object test() {
        return "成功访问test 接口";
    }


    @PostMapping("/login")
    public Object login(@RequestBody PhoneLoginDTO phoneLoginDTO, HttpServerResponse httpServerResponse) {

        // 校验手机验证码的合法性
        String code = phoneLoginDTO.getCode();
        HashMap<String, String> codeErr = new HashMap<>();
        if ("".equals(code)) {
            return codeErr.put("name", "验证码能为空");
        } else {
            if (!"9999".equals(code)) {
                return new HashMap<String, String>().put("name", "验证码错误");
            }
        }

        // 调用登录的业务逻辑
        String token = phoneLoginService.login(phoneLoginDTO);
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(1000 * 60 * 10);
        cookie.setDomain("/");
        return "登录成功";
    }
}
