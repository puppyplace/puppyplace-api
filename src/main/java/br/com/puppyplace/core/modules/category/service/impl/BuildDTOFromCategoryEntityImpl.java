package br.com.puppyplace.core.modules.category.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.puppyplace.core.entities.Category;
import br.com.puppyplace.core.modules.category.dto.CategoryDTO;
import br.com.puppyplace.core.modules.category.service.BuildDTOFromCategoryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class BuildDTOFromCategoryEntityImpl implements BuildDTOFromCategoryEntity{

    private final ModelMapper mapper;

    public CategoryDTO execute(Category category) {
        log.info(">>> Building product DTO from product entity");
        var categoryDTO = mapper.map(category, CategoryDTO.class);

        
        log.info(">>> Done");

        return categoryDTO;
    }    

	
}
