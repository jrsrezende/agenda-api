package br.com.jrsr.agendaapi;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Get all categories")
    public void getTasks(){
        try {

            MvcResult result = mockMvc.perform(get("/api/v1/categories")
                    .contentType("application/json")).andReturn();

            assertEquals(200, result.getResponse().getStatus());
        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }
}
