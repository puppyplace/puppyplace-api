package br.com.puppyplace.core.modules.product.service.impl;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.puppyplace.core.entities.Category;
import br.com.puppyplace.core.entities.Product;
import br.com.puppyplace.core.modules.product.dto.ProductDTO;
import br.com.puppyplace.core.modules.product.service.BuildDTOFromProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class BuildDTOFromProductEntityImpl implements BuildDTOFromProductEntity {

    private final ModelMapper mapper;

    public ProductDTO execute(Product product) {
        log.info(">>> Building product DTO from product entity");
        var productDTO = mapper.map(product, ProductDTO.class);

        productDTO.setIdCategories(
            product.getCategories().stream()
            .map(Category::getId)
            .collect(Collectors.toList()));

        log.info(">>> Done");

        return productDTO;
    }    
}
