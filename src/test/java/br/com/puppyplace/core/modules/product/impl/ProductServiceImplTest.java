package br.com.puppyplace.core.modules.product.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.puppyplace.core.commons.exceptions.BusinessException;
import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;
import br.com.puppyplace.core.config.AppConfig;
import br.com.puppyplace.core.entities.Product;
import br.com.puppyplace.core.modules.product.ProductRepository;
import br.com.puppyplace.core.modules.product.dto.ProductDTO;
import br.com.puppyplace.core.modules.product.service.impl.BuildDTOFromProductEntityImpl;
import br.com.puppyplace.core.modules.product.service.impl.BuildProductFromDTOImpl;
import br.com.puppyplace.core.modules.product.service.impl.ProductServiceImpl;

@ExtendWith(SpringExtension.class)
@Import(AppConfig.class)
class ProductServiceImplTest {
    
    @InjectMocks
    private ProductServiceImpl productService;
    
    @Mock
    private ProductRepository productRepository;

    private EasyRandom easyRandom;
    private ProductDTO productDTO;
    private UUID productID;
    private Product product;

    @BeforeEach
    void init(){
        this.easyRandom = new EasyRandom();

        this.productDTO = easyRandom.nextObject(ProductDTO.class);
        this.productID = UUID.randomUUID();
        this.product = easyRandom.nextObject(Product.class);        
        
        var mapper = new ModelMapper();
        ReflectionTestUtils.setField(productService, "buildDTOFromProductEntity", new BuildDTOFromProductEntityImpl(mapper));
        ReflectionTestUtils.setField(productService, "buildProductFromDTO", new BuildProductFromDTOImpl(mapper));
    }

    @Test
    void shouldReturnSuccess_WhenGetExistentProduct(){
        //given
        when(productRepository.findById(productID)).thenReturn(Optional.of(product));

        //when
        var productDTO = productService.get(productID);

        //then
        assertNotNull(productDTO);
        assertEquals(productDTO.getId(), product.getId());
        assertEquals(productDTO.getDescription(), product.getDescription());
        assertEquals(productDTO.getProductCode(), product.getProductCode());
        assertEquals(productDTO.getPrice(), product.getPrice());
        assertEquals(productDTO.getAvatarUrl(), product.getAvatarUrl());

        verify(productRepository, times(1)).findById(productID);
    }

    @Test
    void shouldReturnError_WhenGetInexistentProduct(){
        //given
        when(productRepository.findById(productID)).thenReturn(Optional.ofNullable(null));

        //then
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.get(productID);
        });
        verify(productRepository, times(1)).findById(productID);
    }

    @Test
    void shouldReturnSuccess_WhenCreateANewProduct(){
        //given
        when(productRepository.save(any(Product.class))).thenReturn(product);

        //when
        var productDTOPersited = productService.create(productDTO);

        //then
        assertNotNull(productDTO);
        assertEquals(productDTO.getId(), productDTOPersited.getId());
        assertEquals(productDTO.getDescription(), productDTOPersited.getDescription());
        assertEquals(productDTO.getProductCode(), productDTOPersited.getProductCode());
        assertEquals(productDTO.getPrice(), productDTOPersited.getPrice());
        assertEquals(productDTO.getAvatarUrl(), productDTOPersited.getAvatarUrl());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldReturnError_WhenCreateANewProductThatViolatesDataIntegrity(){
        //given
        when(productRepository.save(any(Product.class)))
            .thenThrow(new DataIntegrityViolationException(""));

        //then
        assertThrows(BusinessException.class, () -> {
            productService.create(productDTO);
        });

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldReturnSuccess_WhenUpdateProduct(){
        //given
        when(productRepository.findById(productID)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        //when
        var productDTOPersited = productService.update(productDTO, productID);

        //then
        assertNotNull(productDTO);
        assertEquals(productDTO.getId(), productDTOPersited.getId());
        assertEquals(productDTO.getDescription(), productDTOPersited.getDescription());
        assertEquals(productDTO.getProductCode(), productDTOPersited.getProductCode());
        assertEquals(productDTO.getPrice(), productDTOPersited.getPrice());
        assertEquals(productDTO.getAvatarUrl(), productDTOPersited.getAvatarUrl());

        verify(productRepository, times(1)).save(any(Product.class));
        verify(productRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void shouldReturnError_WhenUpdateProductThatViolatesDataIntegrity(){
        //given
        when(productRepository.findById(productID))
            .thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class)))
            .thenThrow(new DataIntegrityViolationException(""));

        //then
        assertThrows(BusinessException.class, () -> {
            productService.update(productDTO, productID);
        });

        verify(productRepository, times(1)).save(any(Product.class));
        verify(productRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void shouldReturnSuccess_whenDeleteProduct(){
        //given
        product.setDeleted(false);        
        when(productRepository.findById(productID)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        //when
        productService.delete(productID);

        //then
        verify(productRepository, times(1)).save(any(Product.class));
        var argument = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(argument.capture());
        assertTrue(argument.getValue().isDeleted());
    }

    @Test
    void shouldReturnSuccess_WhenGetPageOfProducts(){
        //given
        var listOfProduct = easyRandom.objects(Product.class, 2)
            .collect(Collectors.toList());
        var pageOfProducts = new PageImpl<>(listOfProduct);
        when(productRepository.findAll(any(Pageable.class))).thenReturn(pageOfProducts);

        //when
        var pageOfProductDTO = productService.list(PageRequest.of(1, 10));

        //then
        assertNotNull(pageOfProductDTO);
        assertEquals(listOfProduct.size(), pageOfProductDTO.getContent().size());
        verify(productRepository, times(1)).findAll(any(Pageable.class));
    }
}
