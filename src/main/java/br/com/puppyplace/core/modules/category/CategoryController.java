package br.com.puppyplace.core.modules.category;

import br.com.puppyplace.core.modules.category.dto.CategoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

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

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable("id") UUID id){
        log.info(">>> [PUT] A new request to get product with ID {}", id);
        var categoryDTOUpdated = categoryService.update(categoryDTO, id);
        log.info(">>> Response: {}", categoryDTOUpdated);

        return ResponseEntity.ok(categoryDTOUpdated);
    }
}
