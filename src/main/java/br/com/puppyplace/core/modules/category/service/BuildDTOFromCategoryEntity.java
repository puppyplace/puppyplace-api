package br.com.puppyplace.core.modules.category.service;

import br.com.puppyplace.core.entities.Category;
import br.com.puppyplace.core.entities.Product;
import br.com.puppyplace.core.modules.category.dto.CategoryDTO;
import br.com.puppyplace.core.modules.product.dto.ProductDTO;

public interface BuildDTOFromCategoryEntity {
	public CategoryDTO execute(Category category);
}
