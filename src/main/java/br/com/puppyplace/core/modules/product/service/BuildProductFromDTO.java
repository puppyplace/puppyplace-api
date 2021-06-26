package br.com.puppyplace.core.modules.product.service;

import br.com.puppyplace.core.entities.Product;
import br.com.puppyplace.core.modules.product.dto.ProductDTO;

public interface BuildProductFromDTO {
    public Product execute(ProductDTO productDTO);
}
