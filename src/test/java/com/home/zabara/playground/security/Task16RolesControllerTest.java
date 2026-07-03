package com.home.zabara.playground.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Task14SecurityConfig requires a Task15JwtService bean; @WebMvcTest doesn't scan
// plain @Component beans, so it's pulled in explicitly here.
@WebMvcTest(controllers = Task16RolesController.class)
@Import(Task15JwtService.class)
class Task16RolesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void unauthenticatedRequestIsRejected() throws Exception {
        mockMvc.perform(get("/playground/security/roles/admin"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void adminEndpointRejectsAPlainUser() throws Exception {
        mockMvc.perform(get("/playground/security/roles/admin").with(httpBasic("alice", "password123")))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminEndpointAcceptsAnAdmin() throws Exception {
        mockMvc.perform(get("/playground/security/roles/admin").with(httpBasic("bob", "password123")))
                .andExpect(status().isOk());
    }

    @Test
    void userEndpointAcceptsBothRoles() throws Exception {
        mockMvc.perform(get("/playground/security/roles/user").with(httpBasic("alice", "password123")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/playground/security/roles/user").with(httpBasic("bob", "password123")))
                .andExpect(status().isOk());
    }
}
