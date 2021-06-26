package br.com.puppyplace.core.modules.product.service;

import br.com.puppyplace.core.entities.Product;
import br.com.puppyplace.core.modules.product.dto.ProductDTO;

public interface BuildDTOFromProductEntity {
    public ProductDTO execute(Product product);
}
