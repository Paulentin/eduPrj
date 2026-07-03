package com.home.zabara.playground.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Assumes Task14SecurityConfig wires the filter as
 * {@code new Task17RateLimitFilter(5, 60_000)} — capacity 5, effectively no
 * refill during this test's short runtime — per the TODO comment there.
 *
 * Task14SecurityConfig requires a Task15JwtService bean; @WebMvcTest doesn't
 * scan plain @Component beans, so it's pulled in explicitly below.
 */
@WebMvcTest(controllers = Task17RateLimitController.class)
@Import(Task15JwtService.class)
class Task17RateLimitFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void exceedingTheBurstCapacityReturns429ButAnotherUserIsUnaffected() throws Exception {
        int allowed = 0;
        int limited = 0;
        for (int i = 0; i < 20; i++) {
            int status = mockMvc.perform(get("/playground/security/ratelimit/ping").with(httpBasic("alice", "password123")))
                    .andReturn().getResponse().getStatus();
            if (status == 200) {
                allowed++;
            } else if (status == 429) {
                limited++;
            }
        }

        assertEquals(5, allowed, "exactly the burst capacity (5) of alice's requests should succeed");
        assertEquals(15, limited, "the remaining requests should be rejected with 429 once alice's bucket is empty");

        mockMvc.perform(get("/playground/security/ratelimit/ping").with(httpBasic("bob", "password123")))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
}
