package com.pra.securityPlus.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class PhoneUserVO  implements UserDetails {
    @Setter
    @Getter
    private String id;

    @Setter
    @Getter
    private String phone;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private Set<String> permissions;

    public PhoneUserVO(String id, String phone, String name, Set<String> permissions) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
