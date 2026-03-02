package com.chat.demo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.chat.demo.model.users.RecoveryCode;

@Repository
public interface RecoveryCodeRepository extends MongoRepository<RecoveryCode, String> {

    Optional<RecoveryCode> findByEmailAndCode(String email, String code);

    void deleteByEmail(String email);

} 
