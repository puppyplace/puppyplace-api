package br.com.puppyplace.core.modules.product;

import br.com.puppyplace.core.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>{
    Page<Product> findByCategoriesId(UUID categoryID, Pageable pageable);
}
