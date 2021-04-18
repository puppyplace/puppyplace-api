package br.com.puppyplace.core.modules.product.service.impl;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.puppyplace.core.entities.Category;
import br.com.puppyplace.core.entities.Product;
import br.com.puppyplace.core.modules.product.dto.ProductDTO;
import br.com.puppyplace.core.modules.product.service.BuildProductFromDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class BuildProductFromDTOImpl implements BuildProductFromDTO{

    private final ModelMapper mapper;

    public Product execute(ProductDTO productDTO) {
        log.info(">>> Building product entity from product DTO");
        var product = mapper.map(productDTO, Product.class);

        product.setCategories(
                productDTO.getIdCategories().stream()
                .map(uuid -> new Category(uuid))
                .collect(Collectors.toList()));
        
        log.info(">>> Done");
        
        return product;
    }        
}
