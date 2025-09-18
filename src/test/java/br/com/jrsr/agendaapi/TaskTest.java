package br.com.jrsr.agendaapi;

import br.com.jrsr.agendaapi.dto.TaskPostRequest;
import br.com.jrsr.agendaapi.dto.TaskPutRequest;
import br.com.jrsr.agendaapi.exceptions.TaskNotFoundException;
import br.com.jrsr.agendaapi.repositories.TestCategoryRepository;
import br.com.jrsr.agendaapi.repositories.TestTaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestTaskRepository taskRepository;

    @Autowired
    private TestCategoryRepository categoryRepository;

    private final Faker faker = new Faker();

    @Test
    @DisplayName("Create a task successfully")
    @Order(1)
    public void createTaskSuccessfully(){
        try{
            TaskPostRequest request = new TaskPostRequest();
            request.setName("Test task");
            request.setDate(LocalDate.now().toString());
            request.setPriority(faker.options().option("LOW", "MEDIUM", "HIGH"));
            String categoryId = categoryRepository.findByName("Home").getId().toString();
            request.setCategoryId(categoryId);

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tasks")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(request))).andReturn();

            assertEquals(201, result.getResponse().getStatus());
        } catch (Exception e) {
            Assertions.fail("Test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate all required fields")
    @Order(2)
    public void validateRequiredFields(){
        try{
            TaskPostRequest request = new TaskPostRequest();
            request.setName(null);
            request.setDate(null);
            request.setPriority(null);
            request.setCategoryId(null);

            MvcResult result = mockMvc.perform(post("/api/v1/tasks")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(request))).andReturn();

            Assertions.assertTrue(result.getResponse().getContentAsString().contains("Task name is required"));
            Assertions.assertTrue(result.getResponse().getContentAsString().contains("Task date is required."));
            Assertions.assertTrue(result.getResponse().getContentAsString().contains("Task priority is required."));
            Assertions.assertTrue(result.getResponse().getContentAsString().contains("Task category ID is required."));
        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Update a task successfully")
    @Order(3)
    public void updateTaskSuccessfully(){
        try {
            String taskId = taskRepository.findFirstByName("Test task").getId().toString();
            TaskPutRequest request = new TaskPutRequest();
            request.setName("Test task updated");
            request.setDate(LocalDate.now().plusDays(1).toString());
            request.setPriority(faker.options().option("LOW", "MEDIUM", "HIGH"));
            request.setFinished(true);
            String categoryId = categoryRepository.findByName("Study").getId().toString();
            request.setCategoryId(categoryId);

            MvcResult result = mockMvc.perform(put("/api/v1/tasks/" + taskId)
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(request))).andReturn();

            assertEquals(200, result.getResponse().getStatus());
        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Delete a task successfully")
    @Order(4)
    public void deleteTaskSuccessfully(){
        try {
            String taskId = taskRepository.findFirstByName("Test task updated").getId().toString();

            MvcResult result = mockMvc.perform(delete("/api/v1/tasks/" + taskId)
                    .contentType("application/json")).andReturn();

            assertEquals(200, result.getResponse().getStatus());
        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Get tasks within a date range")
    @Order(5)
    public void getTasks(){
        try {

            String range = LocalDate.now().minusDays(1) + "/" + LocalDate.now();

            MvcResult result = mockMvc.perform(get("/api/v1/tasks/" + range)
                    .contentType("application/json")).andReturn();

            assertEquals(200, result.getResponse().getStatus());
        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }
}
