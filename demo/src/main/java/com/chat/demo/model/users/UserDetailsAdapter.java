package com.chat.demo.model.users;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mongodb.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class UserDetailsAdapter implements UserDetails {

    private final Users usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public @Nullable String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
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
        try {
            return usuario.getEnabled();
        } catch (Exception e) {
            Logger.getLogger(UserDetailsAdapter.class.getName()).log(Level.WARNING, "getEnabled is Null");
        }
        return false;
    }
}
