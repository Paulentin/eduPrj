package com.home.zabara.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EduHomeController {

    private String userNameForGreeting;

    public EduHomeController() {
    }

    @Autowired
    public EduHomeController(@Value("${userNameForGreeting}") String userNameForGreeting) {
        this.userNameForGreeting = userNameForGreeting;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> helloThere() {
        String greeting = StringUtils.isEmpty(userNameForGreeting)
                ? "Hello there, this is sample app"
                : "Hello " + userNameForGreeting + ", have a nice day";
        return ResponseEntity.ok(greeting);
    }
}
