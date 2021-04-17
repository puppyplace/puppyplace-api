package br.com.puppyplace.core.modules.category;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.puppyplace.core.modules.category.dto.CategoryDTO;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/category")
@Validated
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    
    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO categoryDTO){
        log.info(">>> [POST] A new request to category of products with RequestBody {}", categoryDTO);
        categoryDTO = categoryService.create(categoryDTO);
        log.info(">>> Response {}", categoryDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO);
    }

}
