package br.com.puppyplace.core.modules.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;
import br.com.puppyplace.core.modules.product.dto.DetailDTO;
import br.com.puppyplace.core.modules.product.dto.SpecificationDTO;
import br.com.puppyplace.core.modules.variant.service.BuildDTOFromVariantEntity;
import br.com.puppyplace.core.modules.variant.service.BuildVariantFromDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private BuildDTOFromVariantEntity buildDTOFromVariantEntity;

    public ProductDTO execute(Product product) {
        log.info(">>> Building product DTO from product entity");
        var productDTO = mapper.map(product, ProductDTO.class);

        productDTO.setIdCategories(
            product.getCategories().stream()
            .map(Category::getId)
            .collect(Collectors.toList()));

        productDTO.setVariants(
                product.getVariants().stream()
                .map(variant -> buildDTOFromVariantEntity.execute(variant))
        .collect(Collectors.toList()));

        log.info(">>> Done");

        return productDTO;
    }
}
