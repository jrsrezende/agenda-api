package br.com.jrsr.agendaapi.repositories;

import br.com.jrsr.agendaapi.domain.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TestTaskRepository extends JpaRepository<Task, UUID> {
    Task findFirstByName(String name);
}
