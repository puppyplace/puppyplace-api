package br.com.puppyplace.core.modules.category.impl;

import br.com.puppyplace.core.commons.exceptions.BusinessException;
import br.com.puppyplace.core.commons.exceptions.ResourceAlreadyInUseException;
import br.com.puppyplace.core.entities.Category;
import br.com.puppyplace.core.entities.Customer;
import br.com.puppyplace.core.entities.Product;
import br.com.puppyplace.core.modules.category.CategoryRepository;
import br.com.puppyplace.core.modules.category.dto.CategoryDTO;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CategoryServiceImpTest {
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private EasyRandom easyRandom;
    private CategoryDTO categoryDTO;
    private Category category;
    private UUID categoryID;

    @BeforeEach
    void init() {
        this.easyRandom = new EasyRandom();
        this.categoryDTO = easyRandom.nextObject(CategoryDTO.class);
        this.category = easyRandom.nextObject(Category.class);
        this.categoryID = UUID.randomUUID();

        ReflectionTestUtils.setField(categoryService, "mapper", new ModelMapper());
    }

    @Test
    void shouldReturnSuccess_whenCreateANewCategory(){
        // given
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // when
        var categoryDTOPersisted = categoryService.create(categoryDTO);

        // then
        assertNotNull(categoryDTOPersisted);
        assertEquals(categoryDTO.getId(), categoryDTOPersisted.getId());
        assertEquals(categoryDTO.getName(), categoryDTOPersisted.getName());

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void shouldReturnError_whenCreateAnExistentCategory(){
        // given
        when(categoryRepository.findByName(any(String.class))).thenReturn(Optional.of(category));
        // then
        assertThrows(ResourceAlreadyInUseException.class, () -> {
            categoryService.create(categoryDTO);
        });

        verify(categoryRepository, times(0)).save(any(Category.class));
    }

    @Test
    void shouldReturnSuccess_whenUpdateCategory(){
        // given
        when(categoryRepository.findById(categoryID)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // when
        var categoryDTOPersisted = categoryService.update(categoryDTO, categoryID);

        // then
        assertNotNull(categoryDTOPersisted);
        assertEquals(categoryDTO.getId(), categoryDTOPersisted.getId());
        assertEquals(categoryDTO.getName(), categoryDTOPersisted.getName());


        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(categoryRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void shouldReturnError_whenUpdateCategoryThatViolatesDataIntegrity(){
        //given
        when(categoryRepository.findById(categoryID)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenThrow(new DataIntegrityViolationException(""));

        //then
        assertThrows(BusinessException.class, () -> {
            categoryService.update(categoryDTO, categoryID);
        });

        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(categoryRepository, times(1)).findById(any(UUID.class));

    }

    @Test
    void shouldReturnSuccess_whenDeleteCategory(){
        //given
        when(categoryRepository.findById(categoryID)).thenReturn(Optional.of(category));

        //when
        categoryService.delete(categoryID);

        //then
        verify(categoryRepository, times(1)).delete(any(Category.class));
        var argument = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).delete(argument.capture());
    }

    @Test
    void shouldReturnSuccess_WhenGetPageOfCategories(){
        //given
        var listOfCategories = easyRandom.objects(Category.class, 2).collect(Collectors.toList());
        var pageOfCategories = new PageImpl<>(listOfCategories);
        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(pageOfCategories);

        //when
        var pageOfProductDTO = categoryService.list(PageRequest.of(1, 10));

        //then
        assertNotNull(pageOfProductDTO);
        assertEquals(listOfCategories.size(), pageOfProductDTO.getContent().size());
        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
    }
}
