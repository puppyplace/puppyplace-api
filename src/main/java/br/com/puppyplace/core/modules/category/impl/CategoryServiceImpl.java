package br.com.puppyplace.core.modules.category.impl;

import java.util.Date;
import java.util.UUID;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.puppyplace.core.commons.exceptions.BusinessException;
import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;


import br.com.puppyplace.core.entities.Category;
import br.com.puppyplace.core.modules.category.repository.CategoryRepository;
import br.com.puppyplace.core.modules.category.dto.CategoryDTO;
import br.com.puppyplace.core.modules.category.service.BuildCategoryFromDTO;
import br.com.puppyplace.core.modules.category.service.BuildDTOFromCategoryEntity;
import br.com.puppyplace.core.modules.category.service.CategoryService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private BuildCategoryFromDTO buildCategoryFromDTO;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private BuildDTOFromCategoryEntity buildDTOFromCategoryEntity;

	@Override
	public CategoryDTO create(CategoryDTO dto) {
		try {
			var product = buildCategoryFromDTO.execute(dto);
			categoryRepository.save(product);
			dto.setId(product.getId());

			log.info(">>> Entity persisted!");
			return dto;
		} catch (DataIntegrityViolationException e) {
			log.error(">>> An exception occurred! {}", e.getMessage());
			throw new BusinessException("A business exception occurred. Please verify the values of request body.");
		}
	}

	public CategoryDTO update(CategoryDTO dto, UUID id) {
		try {
			log.info(">>> Starting update entity");

			this.findOne(id);
			var product = buildCategoryFromDTO.execute(dto);
			product.setId(id);

			categoryRepository.save(product);

			log.info(">>> Entity persisted!");
			return dto;
		} catch (DataIntegrityViolationException e) {
			log.error(">>> An exception occurred! {}", e.getMessage());
			throw new BusinessException("A business exception ocurred. Please verify the values of request body.");
		}
	}

	@Override
	public void delete(UUID id) {
		log.info(">>> Get product to delete.");
		var product = this.findOne(id);
		//product.setDeleted(Boolean.TRUE);
		//product.setDeletedAt(new Date());
		//categoryRepository.save(product);
		categoryRepository.delete(product);

	}

	private Category findOne(UUID id) {
		log.info(">>> Starting find product with ID {}", id);

		return categoryRepository.findById(id).orElseThrow(() -> {
			log.error(">>> category not found with ID {}", id);
			throw new ResourceNotFoundException("No category found with ID " + id.toString());
		});
	}

	@Override
	public Page<CategoryDTO> list(PageRequest pageable) {
		log.info(">>> Searching products listt from database");

		var pageOfProducts = categoryRepository.findAll(pageable);
		var pageOfProductsDTO = pageOfProducts.map(product -> buildDTOFromCategoryEntity.execute(product));

		log.info(">>> Done");
		return pageOfProductsDTO;
	}

	@Override
	public CategoryDTO get(UUID id) {
		return buildDTOFromCategoryEntity.execute(this.findOne(id));
	}
    
    
    
}
