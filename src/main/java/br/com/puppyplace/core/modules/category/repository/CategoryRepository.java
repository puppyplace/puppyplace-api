package br.com.puppyplace.core.modules.category.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.puppyplace.core.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID>{

}
