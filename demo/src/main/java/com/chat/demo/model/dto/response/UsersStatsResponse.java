package com.chat.demo.model.dto.response;

public record UsersStatsResponse(
    long totalUsers,
    long totalOnline
) {}
