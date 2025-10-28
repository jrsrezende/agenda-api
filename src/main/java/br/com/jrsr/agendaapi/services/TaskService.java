package br.com.jrsr.agendaapi.services;

import br.com.jrsr.agendaapi.domain.entities.Category;
import br.com.jrsr.agendaapi.domain.entities.Task;
import br.com.jrsr.agendaapi.domain.enums.Priority;
import br.com.jrsr.agendaapi.dto.response.TaskResponse;
import br.com.jrsr.agendaapi.dto.response.TasksGroupedByCategoryResponse;
import br.com.jrsr.agendaapi.dto.request.CreateTaskRequest;
import br.com.jrsr.agendaapi.dto.response.TasksGroupedByPriorityResponse;
import br.com.jrsr.agendaapi.dto.request.UpdateTaskRequest;
import br.com.jrsr.agendaapi.exceptions.TaskNotFoundException;
import br.com.jrsr.agendaapi.repositories.CategoryRepository;
import br.com.jrsr.agendaapi.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    public TaskResponse createTask(CreateTaskRequest request) {
        Category categoryReference = categoryRepository.getReferenceById(request.getCategoryId());

        Task task = new Task();
        task.setName(request.getName());
        task.setDate(request.getDate());
        task.setPriority(request.getPriority());
        task.setCategory(categoryReference);
        task.setFinished(false);

        taskRepository.save(task);

        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setName(task.getName());
        response.setDate(task.getDate());
        response.setPriority(task.getPriority());
        response.setFinished(task.getFinished());
        response.setCategoryId(task.getCategory().getId());
        return response;
    }

    public TaskResponse updateTask(UpdateTaskRequest request, UUID taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found. Check the provided ID."));

        Category categoryReference = categoryRepository.getReferenceById(request.getCategoryId());

        task.setName(request.getName());
        task.setDate(request.getDate());
        task.setPriority(request.getPriority());
        task.setFinished(request.getFinished());
        task.setCategory(categoryReference);

        taskRepository.save(task);

        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setName(task.getName());
        response.setDate(task.getDate());
        response.setPriority(task.getPriority());
        response.setFinished(task.getFinished());
        response.setCategoryId(task.getCategory().getId());
        return response;
    }

    public void deleteTask(UUID taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found. Check the provided ID."));
        taskRepository.delete(task);
    }

    public List<Task> getTasks(LocalDate minDate, LocalDate maxDate) {
        return taskRepository.findByDateBetween(minDate, maxDate);
    }

    public List<TasksGroupedByPriorityResponse> getTasksByPriority() {
        return taskRepository.findTasksGroupedByPriority();
    }

    public List<TasksGroupedByCategoryResponse> getTasksByCategory() {
        return taskRepository.findTasksGroupedByCategory();
    }
}
