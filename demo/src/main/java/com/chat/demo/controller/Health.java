package com.chat.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/health")
public class Health {

    @GetMapping("/")
    public ResponseEntity<?> health(@RequestParam(required = false, name = "name") String name) {
        return ResponseEntity.status(HttpStatus.OK).body("Works");
    }
    
}
