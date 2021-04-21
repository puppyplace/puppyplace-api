package br.com.puppyplace.core.modules.category.service;

import java.util.UUID;

import br.com.puppyplace.core.modules.category.dto.CategoryDTO;

public interface CategoryService {    
    public CategoryDTO create(CategoryDTO categoryDTO);
    public void delete(UUID id);
}
