package com.pra.securityPlus;

import com.pra.securityPlus.controller.PhoneLoginDTO;
import com.pra.securityPlus.controller.PhoneUserVO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

@Service
public class PhoneLoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    public String login(PhoneLoginDTO phoneLoginDTO) {
        String phone = phoneLoginDTO.getPhone();

        Authentication authentication = null;

        try {
            // 框架调用userDetailService.loadUserDetail(phone)
            authentication = authenticationManager
                    .authenticate(new PhoneAuthenticationToken(phone));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new RuntimeException("该手机号码还没注册");
            } else {
                throw new RuntimeException(e.getMessage());
            }
        }
        return this.createTokenStringByAuth(authentication);
    }

    /**
     * 创建token
     * @param authentication 全局认证对象
     * @return 返回token
     */
    private String createTokenStringByAuth(Authentication authentication) {
        PhoneUserVO phoneUserVO = null;
        if (authentication instanceof PhoneAuthenticationToken) {
            phoneUserVO = ((PhoneAuthenticationToken) authentication).getPhoneUserVO();
        }
        assert phoneUserVO != null;
        return phoneUserVO.getId();
    }

    public PhoneUserVO getUserInfo(HttpServletRequest request) {
        HashSet<String> authorities = new HashSet<>();
        authorities.add("admin:add");
        authorities.add("admin:update");
        return new PhoneUserVO("123456", "123456", "管理员", authorities);
    }
}
