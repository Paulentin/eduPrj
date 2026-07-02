package com.home.zabara.playground.reactive;

import com.home.zabara.playground.security.Task15JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest doesn't scan plain @Service beans, so Task19ItemService is imported explicitly.
// Track C's Task14SecurityConfig is a Spring Security @Configuration, which @WebMvcTest DOES
// scan regardless of the targeted controller, so its own Task15JwtService dependency has to be
// imported here too.
@WebMvcTest(controllers = Task19ItemController.class)
@Import({Task19ItemService.class, Task15JwtService.class})
class Task19ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getByIdReturnsTheItemAsJson() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/playground/reactive/items/2"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.name").value("second"));
    }

    @Test
    void getByIdReturns404WhenTheItemIsMissing() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/playground/reactive/items/missing"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllReturnsEveryItem() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/playground/reactive/items"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }
}
