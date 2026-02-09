package com.chat.demo.config;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chat.demo.service.JwtService;
import com.chat.demo.service.UserDetailsAdapterService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final UserDetailsAdapterService userDetailsAdapterService;

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        //verificar o cabeçalho se é do tipo bearer ou se esta vazio
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        try {
            final String username = jwtService.getUserNameFromToken(jwt);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            
            if(username != null && auth == null){
                UserDetails user = userDetailsAdapterService.loadUserByUsername(username);
                
                if(jwtService.isValidToken(jwt, user)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((request)));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao processar JWT: " + e.getMessage());
            e.printStackTrace();
        }

        //continua a cadeia de filtros
        filterChain.doFilter(request, response);
    }

}
