package br.com.puppyplace.core.modules.category.service;

import br.com.puppyplace.core.entities.Category;
import br.com.puppyplace.core.modules.category.dto.CategoryDTO;


public interface BuildCategoryFromDTO {
	public Category execute(CategoryDTO categoryDTO);
}