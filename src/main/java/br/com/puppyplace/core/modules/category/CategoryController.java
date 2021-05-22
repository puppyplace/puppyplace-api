package br.com.puppyplace.core.modules.category;

import br.com.puppyplace.core.modules.category.dto.CategoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> get(@PathVariable("id") UUID id){
        log.info(">>> [GET] A new request to get category by ID {}", id);
        var categoryDTO = categoryService.get(id);
        log.info(">>> Response: {}", categoryDTO);

        return ResponseEntity.ok(categoryDTO);
    }

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> list(
            @Valid @RequestParam(value = "size", defaultValue = "10") @Min(1) Integer size,
            @Valid @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page){
        var pageable = PageRequest.of(page, size);

        log.info(">>> [GET] A new request to get list of categories in page {} with size {}", page, size);
        var pageOfCategoriesDTO = categoryService.list(pageable);
        log.info(">>> Response: {}", pageOfCategoriesDTO);

        return ResponseEntity.ok(pageOfCategoriesDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {

        log.info(">>> [DELETE] A new request to delete category with ID {}", id);
        categoryService.delete(id);
        log.info(">>> Category deleted! No response.");

    }

}
