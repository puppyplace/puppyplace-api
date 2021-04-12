package br.com.puppyplace.core.modules.product;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.puppyplace.core.entities.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, UUID>{
    
}
