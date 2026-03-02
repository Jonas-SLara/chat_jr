package com.chat.demo.model.users;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "recoveryCode")
public class RecoveryCode {

    @Id
    private String id;

    private String email;

    private String code;

    @SuppressWarnings("removal")
    @Indexed(expireAfterSeconds = 900)
    private Date createdAt;

}
