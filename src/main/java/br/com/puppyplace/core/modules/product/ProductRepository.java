package br.com.puppyplace.core.modules.product;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.puppyplace.core.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>{
    
}
