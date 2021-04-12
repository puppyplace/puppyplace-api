package br.com.puppyplace.core.modules.product.impl;

import java.util.Date;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.puppyplace.core.commons.exceptions.BusinessException;
import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;
import br.com.puppyplace.core.entities.Category;
import br.com.puppyplace.core.entities.Partner;
import br.com.puppyplace.core.entities.Product;
import br.com.puppyplace.core.modules.product.ProductRepository;
import br.com.puppyplace.core.modules.product.ProductService;
import br.com.puppyplace.core.modules.product.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Value("${id-default-partner}")
    private String ID_DEFAULT_PARTNER;

    @Autowired
    private ProductRepository productRepository;

    private Product findOne(UUID id){
        log.info(">>> Starting find product with ID {}", id);

        var optionalProduct = this.productRepository.findById(id);

        if(optionalProduct.isEmpty()){
            log.error(">>> Product not found with ID {}", id);
            throw new ResourceNotFoundException("No product found with ID " + id.toString());
        }

        return optionalProduct.get();
    }

    public ProductDTO get(UUID id) {
        var product = this.findOne(id);             
        return new ProductDTO(product);
    }

    public ProductDTO create(ProductDTO productDTO) {       
        try{
            log.info(">>> Creating entity from DTO");

            var product = Product.builder()
            .description(productDTO.getDescription())
            .title(productDTO.getTitle())
            .price(productDTO.getPrice())
            .promotionalPrice(productDTO.getPromotionalPrice())
            .stock(productDTO.getStock())
            .avatarUrl(productDTO.getAvatarUrl())
            .categories(
                productDTO.getIdCategories().stream()
                .map(id -> Category.builder().id(id).build())
                .collect(Collectors.toList())
            )
            .specifications(productDTO.getSpecifications())
            .unit(productDTO.getUnit())
            .productCode(productDTO.getProductCode())
            .isbnCode(productDTO.getIsbnCode())
            .partner(new Partner(UUID.fromString(ID_DEFAULT_PARTNER)))
            .build();

            productRepository.save(product);   

            log.info(">>> Entity persisted!");

            return new ProductDTO(product);
        } catch(ConstraintViolationException | DataIntegrityViolationException e){
            log.error(">>> An exception occurred! {}", e.getMessage());
            throw new BusinessException("A business exception ocurred. Please verify the values of request body");
        }
    }

    public ProductDTO update(ProductDTO productDTO, UUID id) {
        try{          
            var product = this.findOne(id);

            log.info(">>> Starting update entity");

            product.setDescription(productDTO.getDescription());
            product.setTitle(productDTO.getTitle());
            product.setPrice(productDTO.getPrice());
            product.setPromotionalPrice(productDTO.getPromotionalPrice());
            product.setStock(productDTO.getStock());
            product.setAvatarUrl(productDTO.getAvatarUrl());
            product.setCategories(
                productDTO.getIdCategories().stream()
                .map(idCategory -> Category.builder().id(idCategory).build())
                .collect(Collectors.toList())
            );
            product.setSpecifications(productDTO.getSpecifications());
            product.setUnit(productDTO.getUnit());
            product.setProductCode(productDTO.getProductCode());
            product.setIsbnCode(productDTO.getIsbnCode());
            product.setPartner(new Partner(UUID.fromString(ID_DEFAULT_PARTNER)));

            productRepository.save(product);   

            log.info(">>> Entity persisted!");

            return new ProductDTO(product);
        } catch(ConstraintViolationException | DataIntegrityViolationException e){
            log.error(">>> An exception occurred! {}", e.getMessage());
            throw new BusinessException("A business exception ocurred. Please verify the values of request body");
        }
    }

    public void delete(UUID id) {
        var product = this.findOne(id);
        product.setDeleted(Boolean.TRUE);
        product.setDeletedAt(new Date());
        productRepository.save(product); 
    }

    public Page<ProductDTO> list(Pageable pageable) {        
        var pageOfProducts = this.productRepository.findAll(pageable);
        return pageOfProducts.map(new Function<Product, ProductDTO>() {
            @Override
            public ProductDTO apply(Product product) {        
                return new ProductDTO(product);
            }
        });
    }
}
