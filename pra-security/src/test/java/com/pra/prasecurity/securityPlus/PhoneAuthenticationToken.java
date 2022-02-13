package com.pra.securityPlus;

import com.pra.securityPlus.controller.PhoneUserVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

public class PhoneAuthenticationToken  extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;

    @Setter
    @Getter
    private  PhoneUserVO phoneUserVO;

    public PhoneAuthenticationToken(Collection<? extends GrantedAuthority> authorities,
                                    Object principal
    ) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    public PhoneAuthenticationToken(Object phone){
        super(null);
        this.principal = phone;
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
