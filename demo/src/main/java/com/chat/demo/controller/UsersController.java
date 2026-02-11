package com.chat.demo.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.demo.model.dto.response.UserResponse;
import com.chat.demo.service.users.GetUsersService;
import com.chat.demo.service.users.UserStatsService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Operações Sobre Os Usuários e Dash")
@RequiredArgsConstructor
public class UsersController {

    private final UserStatsService userStatsService;
    private final GetUsersService getUsersService;

    @GetMapping("/stats")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> stats(){
        return ResponseEntity.ok().body(userStatsService.getStats());
    }

    @GetMapping("/{uuid}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getUserById(@PathParam(value = "uuid") UUID uuid){
        return ResponseEntity.ok().body(getUsersService.getByUUID(uuid));
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<UserResponse>> list(Pageable pageable){
        return ResponseEntity.ok().body(getUsersService.list(pageable));
    }
}