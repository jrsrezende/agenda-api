package br.com.jrsr.agendaapi.dto.response;

import br.com.jrsr.agendaapi.domain.enums.Priority;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TasksGroupedByPriorityResponse {
    private String priority;
    private Integer tasksQuantity;

    public TasksGroupedByPriorityResponse(Priority priority, Long tasksQuantity) {
        this.priority = priority.name(); // converte enum para String
        this.tasksQuantity = tasksQuantity.intValue(); // converte Long para Integer
    }
}


