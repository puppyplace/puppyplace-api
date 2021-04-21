package br.com.puppyplace.core.modules.category.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.puppyplace.core.commons.exceptions.BusinessException;
import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;


import br.com.puppyplace.core.entities.Category;
import br.com.puppyplace.core.modules.category.CategoryRepository;
import br.com.puppyplace.core.modules.category.dto.CategoryDTO;
import br.com.puppyplace.core.modules.category.service.CategoryService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO categoryDTO) {           
        log.info(">>> Building category entity from category DTO");
        
        var category = Category.builder()
                        .name(categoryDTO.getName())
                        .build();

        log.info(">>> Done");

        categoryRepository.save(category);
        log.info(">>> Category persisted with ID {}", category.getId());
        
        return mapper.map(category, CategoryDTO.class);
    }
    
    public void delete(UUID id) {
        log.info(">>> Get product to delete.");
        var category = this.findOne(id);
        categoryRepository.delete(category);
    }
    
    private Category findOne(UUID id) {
        log.info(">>> Starting find product with ID {}", id);

        return categoryRepository.findById(id).orElseThrow(() -> {
            log.error(">>> Product not found with ID {}", id);
            throw new ResourceNotFoundException("No product found with ID " + id.toString());
        });
    }
    
    
    
}
