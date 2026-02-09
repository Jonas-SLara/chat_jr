package com.chat.demo.model.message;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Document(collection = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    private String id;

    @Field(name = "user_id")
    private UUID userId;

    @Field(name = "email")
    private String email;

    @Field(name = "userName")
    private String userName;

    @Field(name = "created_at")
    @Indexed(expireAfter = "24h")
    private Instant createdAt;

    //sub documentos
    @Field(name = "medias")
    private List<Media> medias;

    @Field(name = "likes")
    private Long likes;

    @Field(name = "dislikes")
    private Long dislikes;

    @Field(name = "description")
    private String description;

}
