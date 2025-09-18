package br.com.jrsr.agendaapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskPostRequest {

    @Size(min = 8, message = "Task name must be at least 8 characters long")
    @NotEmpty(message = "Task name is required.")
    private String name;

    @NotEmpty(message = "Task date is required.")
    private String date;

    @NotEmpty(message = "Task priority is required.")
    private String priority;

    @NotEmpty(message = "Task category ID is required.")
    private String categoryId;
}

