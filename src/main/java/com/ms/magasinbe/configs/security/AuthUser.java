package com.ms.magasinbe.configs.security;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.magasinbe.common.enums.UserRole;
import com.ms.magasinbe.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


@Getter
@Setter
@AllArgsConstructor
public class AuthUser implements UserDetails {

    private String id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private UserRole userRole;
    private final boolean enabled;

    public AuthUser(String id, String username, UserRole role) {
        this.id = id;
        this.username = username;
        this.userRole = role;
        this.enabled = true;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
