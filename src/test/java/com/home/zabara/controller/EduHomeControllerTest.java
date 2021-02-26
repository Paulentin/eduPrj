package com.home.zabara.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class EduHomeControllerTest {

    EduHomeController eduHomeController = new EduHomeController();

    @BeforeEach
    void setUp() {
    }

    @Test
    void hello_ShouldReturnMessageWithoutName() {
        ResponseEntity<String> stringResponseEntity = eduHomeController.helloThere();
        assertEquals("Hello there, this is sample app", stringResponseEntity.getBody());
    }


    @Test
    void hello_SetUserNameForGreeting_ShouldReturnMessageWithUserName() {
        String expectedUserNameForGreeting = "CheckName";
        eduHomeController = new EduHomeController(expectedUserNameForGreeting);
        ResponseEntity<String> stringResponseEntity = eduHomeController.helloThere();
        assertEquals("Hello " + expectedUserNameForGreeting + ", have a nice day", stringResponseEntity.getBody());
    }
}