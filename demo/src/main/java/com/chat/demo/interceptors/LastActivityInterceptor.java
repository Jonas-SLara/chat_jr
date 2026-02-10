package com.chat.demo.interceptors;


import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.chat.demo.service.UserDetailsAdapterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LastActivityInterceptor implements HandlerInterceptor{

    private final UserDetailsAdapterService userDetailsAdapterService;

    private final Logger logger = Logger.getLogger(LastActivityInterceptor.class.getName());
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        logger.log(Level.INFO, "last actitivy interceptor ativado");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //nao roda para anonimo
        if(auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)){
            String username = auth.getName();
            userDetailsAdapterService.updateLastActivity(username);
            logger.log(Level.INFO, "last activity atualizado");
        }

        return true;
    }

}
