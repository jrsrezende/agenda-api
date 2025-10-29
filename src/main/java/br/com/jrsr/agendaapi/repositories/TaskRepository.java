package br.com.jrsr.agendaapi.repositories;

import br.com.jrsr.agendaapi.domain.entities.Task;
import br.com.jrsr.agendaapi.dto.response.TasksGroupedByCategoryResponse;
import br.com.jrsr.agendaapi.dto.response.TasksGroupedByPriorityResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByDateBetweenOrderByDateAsc(LocalDate dateAfter, LocalDate dateBefore);

    @Query("SELECT new br.com.jrsr.agendaapi.dto.response.TasksGroupedByPriorityResponse(t.priority, COUNT(t)) FROM Task t " +
            "GROUP BY t.priority")
    List<TasksGroupedByPriorityResponse> findTasksGroupedByPriority();

    @Query("SELECT new br.com.jrsr.agendaapi.dto.response.TasksGroupedByCategoryResponse(t.category.name, Count(t)) FROM Task t " +
            "GROUP BY t.category.name")
    List<TasksGroupedByCategoryResponse> findTasksGroupedByCategory();
}
