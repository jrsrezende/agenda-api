package br.com.jrsr.agendaapi.dto.request;

import br.com.jrsr.agendaapi.domain.enums.Priority;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
public class UpdateTaskRequest {

    @Size(min = 8, message = "Task name must be at least 8 characters long")
    @NotEmpty(message = "Task name is required")
    private String name;

    @NotNull(message = "Task date is required")
    private LocalDate date;

    @NotNull(message = "Task priority is required")
    private Priority priority;

    @NotNull(message = "Task finished status is required")
    private Boolean finished;

    @NotNull(message = "Task category ID is required")
    private UUID categoryId;

}
