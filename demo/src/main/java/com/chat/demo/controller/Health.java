package com.chat.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/health")
public class Health {

    private static final Logger LOG  = Logger.getLogger(Health.class.getName());

    @GetMapping("/")
    public ResponseEntity<?> health( @RequestParam(required = false, name = "name") String name,  @RequestHeader("User-Agent") String userAgent ) {
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("Authorization", "secret ...");
        LOG.log(Level.INFO, userAgent);

        return ResponseEntity
            .status(HttpStatus.OK)
            .headers(responseHeader)
            .body("Works");
    }
    
}
