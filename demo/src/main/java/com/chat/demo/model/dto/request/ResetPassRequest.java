package com.chat.demo.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPassRequest(
    @NotBlank
    @Email(message = "email inválido")
    String email,

    @NotBlank
    String password,

    @NotBlank
    @Size(min = 6, max = 6, message = "o código de validação deve ter 6 caracteres")
    String code
) {}
