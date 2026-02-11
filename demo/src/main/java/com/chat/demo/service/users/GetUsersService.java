package com.chat.demo.service.users;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chat.demo.model.dto.response.UserResponse;
import com.chat.demo.model.mappers.UserMapper;
import com.chat.demo.model.users.Users;
import com.chat.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUsersService {

    private final UserRepository userRepository;

    public UserResponse getByUUID(UUID uuid){
        Optional<Users> users = userRepository.findById(uuid);
        Users user = users.orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toResponse(user);
    }

    public UserResponse getByEmail(String email){
        Optional<Users> users = userRepository.findByEmail(email);
        Users user = users.orElseThrow(() -> new RuntimeException("User Not Found"));
        return UserMapper.toResponse(user);
    }

    public Page<UserResponse> list(Pageable pageable){
        return userRepository.findAll(pageable)
            .map(u -> UserMapper.toResponse(u));
    }
}
