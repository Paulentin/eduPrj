package com.home.zabara.playground.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest doesn't scan plain @Component beans, so the controller's and
// Task14SecurityConfig's shared Task15JwtService dependency is imported explicitly.
@WebMvcTest(controllers = Task15JwtController.class)
@Import(Task15JwtService.class)
class Task15JwtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void helloWithoutATokenIsRejected() throws Exception {
        mockMvc.perform(get("/playground/security/jwt/hello"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginThenUsingTheIssuedTokenSucceeds() throws Exception {
        String token = mockMvc.perform(post("/playground/security/jwt/login").param("username", "carol"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(get("/playground/security/jwt/hello").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, carol! (via JWT)"));
    }

    @Test
    void tamperedTokenIsRejected() throws Exception {
        String token = mockMvc.perform(post("/playground/security/jwt/login").param("username", "carol"))
                .andReturn().getResponse().getContentAsString();
        String tampered = token.substring(0, token.length() - 1) + (token.endsWith("A") ? "B" : "A");

        mockMvc.perform(get("/playground/security/jwt/hello").header("Authorization", "Bearer " + tampered))
                .andExpect(status().isUnauthorized());
    }
}
