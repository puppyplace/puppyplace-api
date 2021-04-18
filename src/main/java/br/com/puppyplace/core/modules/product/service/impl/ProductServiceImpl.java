package br.com.puppyplace.core.modules.product.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.puppyplace.core.commons.exceptions.BusinessException;
import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;
import br.com.puppyplace.core.entities.Product;
import br.com.puppyplace.core.modules.product.ProductRepository;
import br.com.puppyplace.core.modules.product.dto.ProductDTO;
import br.com.puppyplace.core.modules.product.service.BuildDTOFromProductEntity;
import br.com.puppyplace.core.modules.product.service.BuildProductFromDTO;
import br.com.puppyplace.core.modules.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Value("${id-default-partner}")
    private String ID_DEFAULT_PARTNER;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BuildProductFromDTO buildProductFromDTO;

    @Autowired
    private BuildDTOFromProductEntity buildDTOFromProductEntity;

    public ProductDTO get(UUID id) {
        return buildDTOFromProductEntity.execute(this.findOne(id));
    }

    public ProductDTO create(ProductDTO productDTO) {
        try {            
            if(productCodeIsEmpty(productDTO)){
                generateProductCode(productDTO);
            }

            var product = buildProductFromDTO.execute(productDTO);
            productRepository.save(product);
            productDTO.setId(product.getId());

            log.info(">>> Entity persisted!");
            return productDTO;
        } catch (DataIntegrityViolationException e) {
            log.error(">>> An exception occurred! {}", e.getMessage());
            throw new BusinessException("A business exception occurred. Please verify the values of request body.");
        }
    }

    public ProductDTO update(ProductDTO productDTO, UUID id) {
        try {  
            log.info(">>> Starting update entity");

            this.findOne(id);
            var product = buildProductFromDTO.execute(productDTO);
            product.setId(id);

            productRepository.save(product);

            log.info(">>> Entity persisted!");
            return productDTO;
        } catch (DataIntegrityViolationException e) {
            log.error(">>> An exception occurred! {}", e.getMessage());
            throw new BusinessException("A business exception ocurred. Please verify the values of request body.");
        }
    }

    public void delete(UUID id) {
        log.info(">>> Get product to delete.");
        var product = this.findOne(id);
        product.setDeleted(Boolean.TRUE);
        product.setDeletedAt(new Date());
        productRepository.save(product);
    }

    public Page<ProductDTO> list(Pageable pageable) {
        log.info(">>> Searching products listt from database");
        
        var pageOfProducts = productRepository.findAll(pageable);
        var pageOfProductsDTO = pageOfProducts.map(product -> buildDTOFromProductEntity.execute(product));

        log.info(">>> Done");
        return pageOfProductsDTO;
    }

    private Product findOne(UUID id) {
        log.info(">>> Starting find product with ID {}", id);

        return productRepository.findById(id).orElseThrow(() -> {
            log.error(">>> Product not found with ID {}", id);
            throw new ResourceNotFoundException("No product found with ID " + id.toString());
        });
    }

    private void generateProductCode(ProductDTO productDTO){
        log.info(">>> Generate product code to new product");
        productDTO.setProductCode(String.valueOf(System.currentTimeMillis()));
    }

    private boolean productCodeIsEmpty(ProductDTO productDTO) {
        log.info(">>> Checking if new product has pre inputed product code");
        return productDTO.getProductCode() == null || productDTO.getProductCode().isEmpty();
    }
}
