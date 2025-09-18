package br.com.jrsr.agendaapi.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskCategoryResponse {
    private String categoryName;
    private Integer tasksQuantity;

    public TaskCategoryResponse(String categoryName, Long tasksQuantity) {
        this.categoryName = categoryName; // converte enum para String
        this.tasksQuantity = tasksQuantity.intValue(); // converte Long para Integer
    }
}
