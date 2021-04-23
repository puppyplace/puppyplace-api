package br.com.puppyplace.core.modules.category.service.impl;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.puppyplace.core.entities.Category;
import br.com.puppyplace.core.entities.Product;
import br.com.puppyplace.core.modules.category.dto.CategoryDTO;
import br.com.puppyplace.core.modules.category.service.BuildCategoryFromDTO;
import br.com.puppyplace.core.modules.product.service.impl.BuildProductFromDTOImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class BuildCategoryFromDTOImpl implements BuildCategoryFromDTO {
	
	 private final ModelMapper mapper;

	@Override
	public Category execute(CategoryDTO categoryDTO) {
		log.info(">>> Building category entity from product DTO");
		var product = mapper.map(categoryDTO, Category.class);
		

		log.info(">>> Done");

		return product;
	}

}
