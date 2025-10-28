package br.com.jrsr.agendaapi.dto.response;

import br.com.jrsr.agendaapi.domain.enums.Priority;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
public class TaskResponse {

    private UUID id;

    private String name;

    private LocalDate date;

    private Priority priority;

    private Boolean finished;

    private UUID categoryId;
}
