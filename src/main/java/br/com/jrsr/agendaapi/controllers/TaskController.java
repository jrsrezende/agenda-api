package br.com.jrsr.agendaapi.controllers;

import br.com.jrsr.agendaapi.dto.TaskCategoryResponse;
import br.com.jrsr.agendaapi.dto.TaskPostRequest;
import br.com.jrsr.agendaapi.dto.TaskPriorityResponse;
import br.com.jrsr.agendaapi.dto.TaskPutRequest;
import br.com.jrsr.agendaapi.domain.entities.Task;
import br.com.jrsr.agendaapi.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Tasks", description = "Operations related to tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new task", description = "Creates a task with name, date, priority, and category")
    @ApiResponse(responseCode = "201", description = "Task created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody @Valid TaskPostRequest request) {
        service.createTask(request);
        return ResponseEntity.status(201).body("Task created successfully");
    }

    @Operation(summary = "Update an existing task", description = "Updates the task details using its ID")
    @ApiResponse(responseCode = "200", description = "Task updated successfully")
    @ApiResponse(responseCode = "404", description = "Task not found. Check the provided ID")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@RequestBody @Valid TaskPutRequest request, @PathVariable UUID id) {
        service.updateTask(request, id);
        return ResponseEntity.status(200).body("Task updated successfully");
    }

    @Operation(summary = "Delete a task", description = "Deletes a task by its ID")
    @ApiResponse(responseCode = "200", description = "Task deleted successfully")
    @ApiResponse(responseCode = "404", description = "Task not found. Check the provided ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable UUID id) {
        service.deleteTask(id);
        return ResponseEntity.status(200).body("Task deleted successfully");
    }

    @Operation(summary = "Get tasks within a date range", description = "Retrieves all tasks between minDate and maxDate")
    @ApiResponse(responseCode = "200", description = "List of tasks returned successfully")
    @GetMapping("/{minDate}/{maxDate}")
    public ResponseEntity<List<Task>> getTasks(@PathVariable LocalDate minDate, @PathVariable LocalDate maxDate) {
        List<Task> tasks = service.getTasks(minDate, maxDate);
        return ResponseEntity.status(200).body(tasks);
    }

    @Operation(summary = "Get tasks grouped by priority", description = "Returns task counts grouped by priority")
    @ApiResponse(responseCode = "200", description = "Task priorities returned successfully")
    @GetMapping("/groupby-priority")
    public ResponseEntity<List<TaskPriorityResponse>> getTasksByPriority() {
        List<TaskPriorityResponse> tasks = service.getTasksByPriority();
        return ResponseEntity.status(200).body(tasks);
    }

    @Operation(summary = "Get tasks grouped by category", description = "Returns task counts grouped by category")
    @ApiResponse(responseCode = "200", description = "Task categories returned successfully")
    @GetMapping("/groupby-category")
    public ResponseEntity<List<TaskCategoryResponse>> getTasksByCategory() {
        List<TaskCategoryResponse> tasks = service.getTasksByCategory();
        return ResponseEntity.status(200).body(tasks);
    }
}
