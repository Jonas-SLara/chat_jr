package com.chat.demo.service.users;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.chat.demo.model.dto.request.LoginRequest;
import com.chat.demo.model.dto.response.LoginResponse;
import com.chat.demo.model.users.UserDetailsAdapter;
import com.chat.demo.service.JwtService;
import com.chat.demo.service.UserDetailsAdapterService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final Logger logger = Logger.getLogger(AuthService.class.getName());

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsAdapterService userDetailsAdapterService;

    @Transactional
    public LoginResponse login(LoginRequest dto){

        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                dto.email(),
                dto.password()
            )
        );

        UserDetailsAdapter user = (UserDetailsAdapter) auth.getPrincipal();
        String token = jwtService.generateTokenDefault(user);
        Date expiration = jwtService.getExpirationFromToken(token);

        userDetailsAdapterService.updateLastActivity(dto.email());

        logger.log(Level.INFO, "Autenticação realizada com Sucesso");
        
        return new LoginResponse(token, expiration);
    }
}
