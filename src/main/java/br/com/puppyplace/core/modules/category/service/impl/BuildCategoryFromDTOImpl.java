package br.com.puppyplace.core.modules.category.service.impl;

import br.com.puppyplace.core.entities.Category;
import br.com.puppyplace.core.modules.category.dto.CategoryDTO;
import br.com.puppyplace.core.modules.category.service.BuildCategoryFromDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class BuildCategoryFromDTOImpl implements BuildCategoryFromDTO {
	
	 private final ModelMapper mapper;

	@Override
	public Category execute(CategoryDTO categoryDTO) {
		log.info(">>> Building category entity from product DTO");
		var category = mapper.map(categoryDTO, Category.class);

		log.info(">>> Done");
		return category;
	}

}
