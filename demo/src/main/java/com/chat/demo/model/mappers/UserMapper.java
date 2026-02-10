package com.chat.demo.model.mappers;

import java.time.LocalDateTime;

import com.chat.demo.model.dto.request.UserRequest;
import com.chat.demo.model.dto.response.UserResponse;
import com.chat.demo.model.users.Users;

public class UserMapper {

    public static Users toEntity(UserRequest request){
        Users users = Users.builder()
        .email(request.email())
        .name(request.name())
        .created(LocalDateTime.now())
        .build();

        return users;
    }

    public static UserResponse toResponse(Users users){
        return new UserResponse(users.getId(), users.getName(), users.getEmail());
    }
}
