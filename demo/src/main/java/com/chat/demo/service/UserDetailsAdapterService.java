package com.chat.demo.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chat.demo.model.users.UserDetailsAdapter;
import com.chat.demo.model.users.Users;
import com.chat.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsAdapterService implements UserDetailsService{
    
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> u = userRepository.findByEmail(username);
        if(!u.isPresent()) {
            throw new UsernameNotFoundException("Usuario não encontrado");
        }

        return new UserDetailsAdapter(u.get());
    }
    
}
