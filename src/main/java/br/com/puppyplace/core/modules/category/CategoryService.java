package br.com.puppyplace.core.modules.category;

import br.com.puppyplace.core.modules.category.dto.CategoryDTO;

import java.util.UUID;

public interface CategoryService {
    public CategoryDTO create(CategoryDTO categoryDTO);
    public CategoryDTO update(CategoryDTO categoryDTO, UUID id);
    public CategoryDTO get(UUID id);
//    public Page<ProductDTO> list(Pageable pageable);
    public void delete(UUID id);
}
