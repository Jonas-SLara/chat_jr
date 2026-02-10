package com.chat.demo.service.users;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chat.demo.model.dto.request.UserRequest;
import com.chat.demo.model.dto.response.UserResponse;
import com.chat.demo.model.mappers.UserMapper;
import com.chat.demo.model.users.Users;
import com.chat.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostUserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserResponse post(UserRequest request){
        Users user = UserMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user = userRepository.save(user);
        return UserMapper.toResponse(user);
    }
}
