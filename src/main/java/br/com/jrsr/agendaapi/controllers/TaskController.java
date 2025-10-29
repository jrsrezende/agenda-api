package br.com.jrsr.agendaapi.controllers;

import br.com.jrsr.agendaapi.dto.response.TaskResponse;
import br.com.jrsr.agendaapi.dto.response.TasksGroupedByCategoryResponse;
import br.com.jrsr.agendaapi.dto.request.CreateTaskRequest;
import br.com.jrsr.agendaapi.dto.response.TasksGroupedByPriorityResponse;
import br.com.jrsr.agendaapi.dto.request.UpdateTaskRequest;
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
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid CreateTaskRequest request) {
        TaskResponse response = service.createTask(request);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Update an existing task", description = "Updates the task details using its ID")
    @ApiResponse(responseCode = "200", description = "Task updated successfully")
    @ApiResponse(responseCode = "404", description = "Task not found. Check the provided ID")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@RequestBody @Valid UpdateTaskRequest request, @PathVariable UUID id) {
        TaskResponse response = service.updateTask(request, id);
        return ResponseEntity.status(200).body(response);
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
    public ResponseEntity<List<TaskResponse>> getTasks(@PathVariable LocalDate minDate, @PathVariable LocalDate maxDate) {
        List<TaskResponse> tasks = service.getTasks(minDate, maxDate);
        return ResponseEntity.status(200).body(tasks);
    }

    @Operation(summary = "Get tasks grouped by priority", description = "Returns task counts grouped by priority")
    @ApiResponse(responseCode = "200", description = "Task priorities returned successfully")
    @GetMapping("/groupby-priority")
    public ResponseEntity<List<TasksGroupedByPriorityResponse>> getTasksByPriority() {
        List<TasksGroupedByPriorityResponse> tasks = service.getTasksByPriority();
        return ResponseEntity.status(200).body(tasks);
    }

    @Operation(summary = "Get tasks grouped by category", description = "Returns task counts grouped by category")
    @ApiResponse(responseCode = "200", description = "Task categories returned successfully")
    @GetMapping("/groupby-category")
    public ResponseEntity<List<TasksGroupedByCategoryResponse>> getTasksByCategory() {
        List<TasksGroupedByCategoryResponse> tasks = service.getTasksByCategory();
        return ResponseEntity.status(200).body(tasks);
    }
}
