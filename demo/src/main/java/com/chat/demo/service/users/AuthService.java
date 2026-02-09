package com.chat.demo.service.users;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.chat.demo.model.dto.request.LoginRequest;
import com.chat.demo.model.dto.response.LoginResponse;
import com.chat.demo.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest dto){
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                dto.email(),
                dto.password()
            )
        );
        UserDetails user = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateTokenDefault(user);
        Date expiration = jwtService.getExpirationFromToken(token);
        return new LoginResponse(token, expiration);
    }
}
