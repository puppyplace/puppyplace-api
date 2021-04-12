package br.com.puppyplace.core.modules.product;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import br.com.puppyplace.core.modules.product.dto.ProductDTO;

public interface ProductService {
    public ProductDTO create(ProductDTO productDTO);
    public ProductDTO update(ProductDTO productDTO, UUID id);
    public void delete(UUID id);
    public ProductDTO get(UUID id);
    public Page<ProductDTO> list(Pageable Pageable);    
}
