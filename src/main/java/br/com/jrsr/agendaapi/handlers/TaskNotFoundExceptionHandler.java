package br.com.jrsr.agendaapi.handlers;

import br.com.jrsr.agendaapi.exceptions.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TaskNotFoundExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Object> handleTaskNotFoundException(TaskNotFoundException e) {

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("timestamp", LocalDateTime.now());
        body.put("error", e.getMessage());

        return ResponseEntity.status(404).body(body);
    }
}
