package br.com.puppyplace.core.modules.category;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.puppyplace.core.entities.Category;

public interface CategoryRepository extends CrudRepository<Category, UUID> {
    
}
