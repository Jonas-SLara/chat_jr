package com.chat.demo.service.users;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.chat.demo.model.dto.response.UsersStatsResponse;
import com.chat.demo.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserStatsService {

    private final UserRepository userRepository;

    @Transactional
    public UsersStatsResponse getStats(){
        long totalOnline = userRepository.getTotalOnlineSince(LocalDateTime.now().minusMinutes(2));
        long totalUsers = userRepository.getTotalUsers();
        return new UsersStatsResponse(totalUsers, totalOnline);
    }
}
