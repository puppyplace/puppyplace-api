package br.com.puppyplace.core.modules.category;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.puppyplace.core.entities.Category;

import javax.swing.text.html.Option;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByName(String name);
    
}
