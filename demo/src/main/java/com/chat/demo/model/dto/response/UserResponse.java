package com.chat.demo.model.dto.response;

import java.util.UUID;

public record UserResponse(
    UUID id,
    String name,
    String email
) { }
