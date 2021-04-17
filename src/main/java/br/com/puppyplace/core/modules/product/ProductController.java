package br.com.puppyplace.core.modules.product;

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

import br.com.puppyplace.core.modules.product.dto.ProductDTO;
import br.com.puppyplace.core.modules.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/product")
@Validated
@Slf4j
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping
	public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO productDTO){
		log.info(">>> [POST] A new product to create received. RequestBody: {}", productDTO);
		var product = productService.create(productDTO);
		log.info(">>> Response: {}", product);

		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> list(
		@Valid @RequestParam(value = "size", defaultValue = "10") @Min(1) Integer size,
		@Valid @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page){
		var pageable = PageRequest.of(page, size);
		
		log.info(">>> [GET] A new request to get list of products in page {} with size {}", page, size);
		var pageOfProductsDTO = productService.list(pageable);
		log.info(">>> Response: {}", pageOfProductsDTO);

		return ResponseEntity.ok(pageOfProductsDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> get(@PathVariable("id") UUID id){
		log.info(">>> [GET] A new request to get product with ID {}", id);
		var productDTO = productService.get(id);
		log.info(">>> Response: {}", productDTO);

		return ResponseEntity.ok(productDTO); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody ProductDTO productDTO, @PathVariable("id") UUID id){

		log.info(">>> [PUT] A new request to get product with ID {}", id);
		var productDTOUpdated = productService.update(productDTO, id);
		log.info(">>> Response: {}", productDTOUpdated);

		return ResponseEntity.ok(productDTOUpdated);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") UUID id) {

		log.info(">>> [DELETE] A new request to delete product with ID {}", id);
		productService.delete(id);
		log.info(">>> Product deleted! No response.");
		
	}
	
}
