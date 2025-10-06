package br.com.jrsr.agendaapi.config;

import br.com.jrsr.agendaapi.domain.entities.Category;
import br.com.jrsr.agendaapi.repositories.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initCategories(CategoryRepository repository) {
        return args -> {
            List<String> categoryNames = List.of("Work", "Leisure", "Study", "Health", "Home", "Family", "Others");

            categoryNames.stream()
                    .filter(name -> repository.findByName(name).isEmpty()) // só insere se não existir
                    .map(name -> new Category(null, name))
                    .forEach(repository::save);
        };
    }
}

