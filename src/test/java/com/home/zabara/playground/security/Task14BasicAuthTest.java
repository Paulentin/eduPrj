package com.home.zabara.playground.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Task14SecurityConfig requires a Task15JwtService bean (for the C2 wiring instructions);
// @WebMvcTest doesn't scan plain @Component beans, so it's pulled in explicitly here.
@WebMvcTest(controllers = Task14BasicAuthController.class)
@Import(Task15JwtService.class)
class Task14BasicAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void unauthenticatedRequestIsRejected() throws Exception {
        mockMvc.perform(get("/playground/security/basic/hello"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void wrongPasswordIsRejected() throws Exception {
        mockMvc.perform(get("/playground/security/basic/hello").with(httpBasic("alice", "wrong-password")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void correctCredentialsAreAccepted() throws Exception {
        mockMvc.perform(get("/playground/security/basic/hello").with(httpBasic("alice", "password123")))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, alice!"));
    }
}
