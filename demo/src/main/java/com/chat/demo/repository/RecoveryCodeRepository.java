package com.chat.demo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.chat.demo.model.users.RecoveryCode;

@Repository
public interface RecoveryCodeRepository extends MongoRepository<RecoveryCode, String> {

    @Query("{ 'email': ?0, 'code': ?1 }")
    Optional<RecoveryCode> findByEmailAndCode(String email, String code);

    @Query(value = "{ 'email': ?0 }", delete = true)
    void deleteByEmail(String email);

} 
