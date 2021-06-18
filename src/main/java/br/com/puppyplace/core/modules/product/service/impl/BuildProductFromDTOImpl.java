package br.com.puppyplace.core.modules.product.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;
import br.com.puppyplace.core.modules.category.CategoryRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BuildProductFromDTOImpl implements BuildProductFromDTO {

    private final ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product execute(ProductDTO productDTO) {
        log.info(">>> Building product entity from product DTO");

        var product = mapper.map(productDTO, Product.class);

        List<Category> categories = productDTO.getIdCategories()
                .stream()
                .map(this::validCategory)
                .collect(Collectors.toList());

        product.setCategories(categories);
        
        log.info(">>> Done");
        
        return product;
    }

    private Category validCategory(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.error(">>> category not found with ID {}", categoryId);
                    throw new ResourceNotFoundException("No Category found with ID " + categoryId);
                });
    }
}
