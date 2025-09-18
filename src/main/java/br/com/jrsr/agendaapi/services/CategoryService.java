package br.com.jrsr.agendaapi.services;

import br.com.jrsr.agendaapi.domain.entities.Category;
import br.com.jrsr.agendaapi.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() {
        return repository.findAll();
    }
}
