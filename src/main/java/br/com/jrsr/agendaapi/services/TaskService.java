package br.com.jrsr.agendaapi.services;

import br.com.jrsr.agendaapi.domain.entities.Category;
import br.com.jrsr.agendaapi.domain.entities.Task;
import br.com.jrsr.agendaapi.domain.enums.Priority;
import br.com.jrsr.agendaapi.dto.TaskCategoryResponse;
import br.com.jrsr.agendaapi.dto.TaskPostRequest;
import br.com.jrsr.agendaapi.dto.TaskPriorityResponse;
import br.com.jrsr.agendaapi.dto.TaskPutRequest;
import br.com.jrsr.agendaapi.exceptions.TaskNotFoundException;
import br.com.jrsr.agendaapi.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public void createTask(TaskPostRequest request) {
        Task task = new Task();
        task.setName(request.getName());
        task.setDate(LocalDate.parse(request.getDate()));
        task.setPriority(Priority.valueOf(request.getPriority()));
        task.setCategory(new Category());
        task.getCategory().setId(UUID.fromString(request.getCategoryId()));
        task.setFinished(false);
        repository.save(task);
    }

    public void updateTask(TaskPutRequest request, UUID taskId) {
        Task task = repository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found. Check the provided ID."));
        task.setName(request.getName());
        task.setDate(LocalDate.parse(request.getDate()));
        task.setPriority(Priority.valueOf(request.getPriority()));
        task.setFinished(request.getFinished());
        task.setCategory(new Category());
        task.getCategory().setId(UUID.fromString(request.getCategoryId()));
        repository.save(task);
    }

    public void deleteTask(UUID taskId) {
        Task task = repository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found. Check the provided ID."));
        repository.delete(task);
    }

    public List<Task> getTasks(LocalDate minDate, LocalDate maxDate) {
        return repository.findByDateBetween(minDate, maxDate);
    }

    public List<TaskPriorityResponse> getTasksByPriority() {
        return repository.findTasksGroupedByPriority();
    }

    public List<TaskCategoryResponse> getTasksByCategory() {
        return repository.findTasksGroupedByCategory();
    }
}
