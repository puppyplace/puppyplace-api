package br.com.puppyplace.core.modules.category.service;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.puppyplace.core.modules.category.dto.CategoryDTO;
import br.com.puppyplace.core.modules.product.dto.ProductDTO;

public interface CategoryService {    
    public CategoryDTO create(CategoryDTO categoryDTO);
    public void delete(UUID id);
	public Page<CategoryDTO> list(PageRequest pageable);
	public CategoryDTO get(UUID id);
	public CategoryDTO update(@Valid CategoryDTO dto, UUID id);
}
