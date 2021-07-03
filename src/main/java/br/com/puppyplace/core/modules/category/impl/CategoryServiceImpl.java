package br.com.puppyplace.core.modules.category.impl;

import br.com.puppyplace.core.commons.exceptions.BusinessException;
import br.com.puppyplace.core.commons.exceptions.ResourceAlreadyInUseException;
import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;
import br.com.puppyplace.core.entities.Category;
import br.com.puppyplace.core.entities.Product;
import br.com.puppyplace.core.modules.address.dto.AddressDTO;
import br.com.puppyplace.core.modules.category.CategoryRepository;
import br.com.puppyplace.core.modules.category.CategoryService;
import br.com.puppyplace.core.modules.category.dto.CategoryDTO;
import br.com.puppyplace.core.modules.product.ProductRepository;
import br.com.puppyplace.core.modules.product.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public CategoryDTO create(CategoryDTO categoryDTO) {           
        log.info(">>> Building category entity from category DTO");

        var existingCategory = categoryRepository.findByName(categoryDTO.getName());
        if (existingCategory.isPresent()){
            log.error(">>> Category is already in use. Returning error to client");
            throw new ResourceAlreadyInUseException("Category is already in use");
        }

        var category = mapper.map(categoryDTO, Category.class);
        category.setCreatedAt(new Date());
        category.setUpdatedAt(new Date());

        categoryRepository.save(category);
        categoryDTO.setId(category.getId());
        log.info(">>> Category persisted with ID {}", category.getId());

        return categoryDTO;
    }

    public CategoryDTO update(CategoryDTO categoryDTO, UUID id){
        try {
            log.info(">>> Starting update entity");

            this.findOne(id);
            var category = mapper.map(categoryDTO, Category.class);
            category.setId(id);
            category.setUpdatedAt(new Date());

            categoryRepository.save(category);

            log.info(">>> Entity persisted!");
            categoryDTO.setId(id);
            return categoryDTO;
        } catch (DataIntegrityViolationException e) {
            log.error(">>> An exception occurred! {}", e.getMessage());
            throw new BusinessException("A business exception ocurred. Please verify the values of request body.");
        }
    }

    public CategoryDTO get(UUID id){
        log.info(">>> Building product DTO from product entity");
        var category = this.findOne(id);
        var categoryDTO = mapper.map(category, CategoryDTO.class);

        log.info(">>> Done");
        return categoryDTO;

    }

    public Page<CategoryDTO> list(Pageable pageable) {
        log.info(">>> Searching categories list from database");

        var pageOfCategories = categoryRepository.findAll(pageable);
        var pageOfCategoriesDTO = pageOfCategories.map(
                category -> mapper.map(category, CategoryDTO.class)
        );

        log.info(">>> Done");
        return pageOfCategoriesDTO;
    }

    public void delete(UUID id) {
        log.info(">>> Get category to delete.");
        var category = this.findOne(id);
        categoryRepository.delete(category);
    }

    private Category findOne(UUID id){
        log.info(">>> Starting find category with ID {}", id);

        return categoryRepository.findById(id).orElseThrow(() -> {
            log.error(">>> Category not found with ID {}", id);
            throw new ResourceNotFoundException("No category found with ID " + id.toString());
        });
    }

    public Page<ProductDTO> listProducts(Pageable pageable, UUID categoryID){
        log.info(">>> Searching products list by category from database");

        var pageOfProducts = productRepository.findByCategoriesId(categoryID, pageable);
        var pageOfAddressDTO = pageOfProducts.map(product -> mapper.map(product, ProductDTO.class));

        log.info(">>> Done");
        return pageOfAddressDTO;
    }
}
