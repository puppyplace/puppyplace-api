package br.com.puppyplace.core.modules.category.controller;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	private CategoryService service;
	
	@PostMapping
	public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto){
		log.info(">>> [POST] A new product to create received. RequestBody: {}", dto);
		var product = service.create(dto);
		log.info(">>> Response: {}", product);

		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}
	
	@GetMapping
	public ResponseEntity<Page<CategoryDTO>> list(
		@Valid @RequestParam(value = "size", defaultValue = "100") @Min(1) Integer size,
		@Valid @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page){
		var pageable = PageRequest.of(page, size);
		
		log.info(">>> [GET] A new request to get list of products in page {} with size {}", page, size);
		var pageOfCategoriesDTO = service.list(pageable);
		log.info(">>> Response: {}", pageOfCategoriesDTO);

		return ResponseEntity.ok(pageOfCategoriesDTO);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> get(@PathVariable("id") UUID id){
		log.info(">>> [GET] A new request to get product with ID {}", id);
		var dto = service.get(id);
		log.info(">>> Response: {}", dto);

		return ResponseEntity.ok(dto); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> update(@Valid @RequestBody CategoryDTO dto, @PathVariable("id") UUID id){

		log.info(">>> [PUT] A new request to get product with ID {}", id);
		dto = service.update(dto, id);
		dto.setId(id);
		log.info(">>> Response: {}", dto);

		return ResponseEntity.ok(dto);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") UUID id) {

		log.info(">>> [DELETE] A new request to delete product with ID {}", id);
		service.delete(id);
		log.info(">>> Product deleted! No response.");
		
	}

}
