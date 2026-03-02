package com.chat.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.chat.demo.model.message.Message;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    
} 