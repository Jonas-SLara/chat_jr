package com.chat.demo.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chat.demo.model.users.Users;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

    @Query("SELECT u FROM Users u WHERE u.email = :email")
    public Optional<Users> findByEmail(@Param(value = "email") String email);

    @Transactional
    @Modifying
    @Query("UPDATE Users u SET u.lastActivity = :now where u.email = :email")
    public void updateLatActivity(@Param(value = "email") String email, @Param("now") LocalDateTime now);
}
