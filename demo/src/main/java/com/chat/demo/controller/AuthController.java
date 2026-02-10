package com.chat.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.demo.model.dto.request.LoginRequest;
import com.chat.demo.model.dto.request.UserRequest;
import com.chat.demo.model.dto.response.LoginResponse;
import com.chat.demo.service.users.AuthService;
import com.chat.demo.service.users.PostUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(
    name = "operações relacionadas a autenticação do usuario",
    description = "fazer login e registrar na plataforma"
)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PostUserService postUserService;

    @Operation(summary = "fazer login na plataforma, receber token")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @Operation(summary = "fazer cadastro na plataforma")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarUsuario(@Valid @RequestBody  UserRequest dto) {
        return ResponseEntity.ok(postUserService.post(dto));
    }
    
}
