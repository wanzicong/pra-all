package com.pra.securityPhone.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;


public class PhoneUserDetail extends User {

    @Getter
    @Setter
    private String phone;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Set<String> permissions;


    public PhoneUserDetail(String id,
                           String phone,
                           String name,
                           Collection<? extends GrantedAuthority> authorities) {
        super(phone, "",
                true,
                true,
                true,
                true, authorities);
        this.name = name;
        this.id = id;
        this.phone = phone;
    }
}
