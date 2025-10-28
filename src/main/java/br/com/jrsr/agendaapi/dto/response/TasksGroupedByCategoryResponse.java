package br.com.jrsr.agendaapi.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TasksGroupedByCategoryResponse {
    private String categoryName;
    private Integer tasksQuantity;

    public TasksGroupedByCategoryResponse(String categoryName, Long tasksQuantity) {
        this.categoryName = categoryName; // converte enum para String
        this.tasksQuantity = tasksQuantity.intValue(); // converte Long para Integer
    }
}
