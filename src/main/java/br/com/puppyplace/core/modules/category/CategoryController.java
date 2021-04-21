package br.com.puppyplace.core.modules.category;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.puppyplace.core.modules.category.dto.CategoryDTO;
import br.com.puppyplace.core.modules.category.service.CategoryService;
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
    
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") UUID id) {

		log.info(">>> [DELETE] A new request to delete product with ID {}", id);
		categoryService.delete(id);
		log.info(">>> Product deleted! No response.");
		
	}    

}
