package br.com.puppyplace.core.modules.category.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.puppyplace.core.entities.Category;
import br.com.puppyplace.core.modules.category.CategoryRepository;
import br.com.puppyplace.core.modules.category.CategoryService;
import br.com.puppyplace.core.modules.category.dto.CategoryDTO;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO categoryDTO) {        
        var category = Category.builder()
                        .name(categoryDTO.getName())
                        .build();

        categoryRepository.save(category);
        
        return new CategoryDTO(category);
    }
    
}
