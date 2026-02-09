package com.chat.demo.model.dto.response;

import java.util.Date;

public record LoginResponse(
    String token,
    Date expiration
) {}