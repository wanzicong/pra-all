package com.pra.securityPhone.controller;

import com.pra.securityPhone.SmsCodeAuthenticationToken;
import com.pra.securityPhone.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

/**
 * @author chenyunchang
 * @title
 * @date 2020/10/28 11:10
 * @Description:
 */
@RestController
@RequestMapping
@Slf4j
public class LoginController {

    private static final String LOGIN_CODE = "code:login:";

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;



//    @PostMapping("/login")
//    public Object login(@RequestParam String username,
//                        @RequestParam String password,
//                        HttpServletRequest request) {
//        SysUserDTO sysUserDTO = sysUserService.getUserByUserName(username);
//        if (sysUserDTO == null) {
//            throw new ProjectException(ExceptionEnum.USER_NOT_FOUND);
//        }
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        boolean matches = bCryptPasswordEncoder.matches(password, sysUserDTO.getPassword());
//        if (!matches) {
//            throw new ProjectException(ExceptionEnum.USERNAME_OR_PASSWORD_ERRO);
//        }
//        sysUserDTO.setPassword(null);
//        // ??????????????????
//        return ResultUtils.success("????????????", sysUserDTO);
//    }

//    @PostMapping("/login/phone")
//    public Object loginByPhoneAndCode(String phone,
//                                      String smsCode,
//                                      HttpServletRequest request) {
//
//        //????????????security??????
//        UserDetails userDetails = userDetailsService.loadUserByUsername(phone);
//        SmsCodeAuthenticationToken smsCodeAuthenticationToken = new SmsCodeAuthenticationToken(phone, userDetails.getAuthorities());
//        Authentication authenticate = authenticationManager.authenticate(smsCodeAuthenticationToken);
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(authenticate);
//        HttpSession session = request.getSession(true);
//        //???session?????????security context,???????????????session??????????????????????????????
//        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);
//        return "????????????";
//    }

    @PostMapping("/logout")
    public void logout() {
        log.info("??????????????????");
    }


    @GetMapping("/test")
    public Object logout1() {
        return "test";
    }


//    @PostMapping("/login/phone2")
//    public Object loginByPhoneAndCode2(@RequestParam String phone,
//                                                        @RequestParam String smsCode,
//                                                        HttpServletRequest request) {
//        //1. ?????????????????????
//        String redisPhoneCode = redisTemplate.opsForValue().get(LOGIN_CODE);
//        if (!smsCode.equals(redisPhoneCode)) {
//            throw new ProjectException(400, "???????????????");
//        }
//        //1.????????????????????????????????????????????????
//        SysUserDTO sysUserDTO = sysUserService.getUserByPhone2(phone);
//        return "????????????";
//    }
}