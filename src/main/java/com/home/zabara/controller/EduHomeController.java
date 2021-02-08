package com.home.zabara.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EduHomeController {

    @GetMapping("/hello")
    public ResponseEntity<String> helloThere() {
        return ResponseEntity.ok("Hello there, this is sample app");
    }
}
