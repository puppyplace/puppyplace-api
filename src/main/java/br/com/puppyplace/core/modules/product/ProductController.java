package br.com.puppyplace.core.modules.product;

import java.util.UUID;

import javax.validation.Valid;

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

@RestController
@RequestMapping("/product")
@Validated
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping
	public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO productDTO){
		var productDTOCreated = productService.create(productDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(productDTOCreated);
	}

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> list(
		@RequestParam(value = "size", defaultValue = "10") Integer size,
		@RequestParam(value = "page", defaultValue = "0") Integer page){
		var pageable = PageRequest.of(page, size);
		
		var pageOfProductsDTO = productService.list(pageable);

		return ResponseEntity.ok(pageOfProductsDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> get(@PathVariable("id") UUID id){
		var productDTO = productService.get(id);
		return ResponseEntity.ok(productDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody ProductDTO productDTO, @PathVariable("id") UUID id){
		var productDTOUpdated = productService.update(productDTO, id);
		return ResponseEntity.ok(productDTOUpdated);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") UUID id) {
		productService.delete(id);
	}
	
}
