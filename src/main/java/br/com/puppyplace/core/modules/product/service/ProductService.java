package br.com.puppyplace.core.modules.product.service;

import java.util.UUID;

import br.com.puppyplace.core.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import br.com.puppyplace.core.modules.product.dto.ProductDTO;

public interface ProductService {
    public ProductDTO create(ProductDTO productDTO);
    public ProductDTO update(ProductDTO productDTO, UUID id);
    public void delete(UUID id);
    public ProductDTO get(UUID id);
    public Page<ProductDTO> list(Pageable pageable);
    public Product findOne(UUID id);

}
