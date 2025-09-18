package br.com.jrsr.agendaapi.dto;

import br.com.jrsr.agendaapi.domain.enums.Priority;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskPriorityResponse {
    private String priority;
    private Integer tasksQuantity;

    public TaskPriorityResponse(Priority priority, Long tasksQuantity) {
        this.priority = priority.name(); // converte enum para String
        this.tasksQuantity = tasksQuantity.intValue(); // converte Long para Integer
    }
}


