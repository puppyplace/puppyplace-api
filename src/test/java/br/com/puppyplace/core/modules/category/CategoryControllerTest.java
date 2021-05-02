package br.com.puppyplace.core.modules.category;

import br.com.puppyplace.core.modules.category.controller.CategoryController;
import br.com.puppyplace.core.modules.category.dto.CategoryDTO;
import br.com.puppyplace.core.modules.category.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc httpRequest;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CategoryService categoryService;

    private EasyRandom easyRandom;
    private CategoryDTO categoryDTO;
    private CategoryDTO invalidCategoryDTO;

    @BeforeEach
    void init(){
        this.easyRandom = new EasyRandom();
        this.categoryDTO = easyRandom.nextObject(CategoryDTO.class);
        this.invalidCategoryDTO = CategoryDTO.builder().name("").build();
    }

    @Test
    void shouldReturnSuccess_whenSendAValidCategoryToCreate() throws Exception {
        // given
        when(categoryService.create(any(CategoryDTO.class))).thenReturn(categoryDTO);

        // when
        httpRequest.perform(
                post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(categoryDTO))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(categoryDTO.getId().toString()))
                .andExpect(jsonPath("name").value(categoryDTO.getName()));
    }

    @Test
    void shoulReturnError_whenSendAInvalidCategoryToCreate() throws Exception {
        // when
        httpRequest
                .perform(post("/category").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(invalidCategoryDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("messages").isNotEmpty())
                .andExpect(jsonPath("messages").isArray());

        // then
        verify(categoryService, times(0)).create(invalidCategoryDTO);
    }

    @Test
    void shouldReturnSuccess_whenGetListOfCategories() throws Exception {
        // given
        List<CategoryDTO> categoryList = easyRandom.objects(CategoryDTO.class, 3).collect(Collectors.toList());
        var page = new PageImpl<CategoryDTO>(categoryList);
        when(categoryService.list(any(PageRequest.class))).thenReturn(page);

        // when
        httpRequest.perform(get("/category").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").isArray())
                .andExpect(jsonPath("pageable").isNotEmpty());

        // then
        verify(categoryService, times(1)).list(any(PageRequest.class));
    }

    @ParameterizedTest
    @CsvSource({"size, -1", "page, -2"})
    void shouldReturnError_whenGetListOfCategoriesWithInvalidSizeOrPage(String parameter, String value) throws Exception {
        // when
        httpRequest.perform(get("/category").accept(MediaType.APPLICATION_JSON).param(parameter, value))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("messages").isNotEmpty()).andExpect(jsonPath("messages").isArray());

        // then
        verify(categoryService, times(0)).list(any(PageRequest.class));
    }

    @Test
    void shouldReturnSuccess_whenGetCategoryWithAValidID() throws Exception {
        // given
        when(categoryService.get(any(UUID.class))).thenReturn(categoryDTO);

        // when
        httpRequest.perform(get("/category/{id}", UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(categoryDTO.getId().toString()))
            .andExpect(jsonPath("name").value(categoryDTO.getName()));

        // then
        verify(categoryService, times(1)).get(any(UUID.class));
    }

    @Test
    void shouldReturnError_whenGetCategoryWithInvalidID() throws Exception {
        // when
        httpRequest.perform(get("/category/{id}", "10").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("message").isNotEmpty());

        // then
        verify(categoryService, times(0)).get(any(UUID.class));
    }

    @Test
    void shouldReturnSuccess_whenUpdateCategoryWithValidPayload() throws Exception {
        // given
        when(categoryService.update(any(CategoryDTO.class), any(UUID.class))).thenReturn(categoryDTO);

        // when
        httpRequest
                .perform(put("/category/{id}", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(categoryDTO.getId().toString()))
                .andExpect(jsonPath("name").value(categoryDTO.getName()));

        // then
        verify(categoryService, times(1)).update(any(CategoryDTO.class), any(UUID.class));
    }

    @Test
    void shouldReturnError_whenUpdateCategoryWithInvalidPayload() throws Exception {
        // when
        httpRequest
                .perform(put("/category/{id}", UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(invalidCategoryDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("messages").isNotEmpty())
                .andExpect(jsonPath("messages").isArray());

        // then
        verify(categoryService, times(0)).update(any(CategoryDTO.class), any(UUID.class));
    }

    @Test
    void shouldReturnSuccess_whenDeleteProduct() throws Exception {
        // when
        httpRequest
                .perform(delete("/category/{id}", UUID.randomUUID().toString())
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // then
        verify(categoryService, times(1)).delete(any(UUID.class));
    }

    @Test
    void shouldReturnError_whenDeleteCategoryWithInvalidID() throws Exception {
        // when
        httpRequest
                .perform(delete("/category/{id}", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("message").isNotEmpty());
        // then
        verify(categoryService, times(0)).delete(any(UUID.class));
    }
}
