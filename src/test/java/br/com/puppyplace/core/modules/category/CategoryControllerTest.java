package br.com.puppyplace.core.modules.category;

import br.com.puppyplace.core.modules.category.dto.CategoryDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {
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
        httpRequest.perform(post("/category").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(categoryDTO.getId().toString()))
                .andExpect(jsonPath("name").value(categoryDTO.getName()));

        // then
        verify(categoryService, times(1)).create(categoryDTO);
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

}