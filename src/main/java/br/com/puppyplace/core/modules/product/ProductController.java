package br.com.puppyplace.core.modules.product;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.puppyplace.core.modules.product.dto.ProductDTO;

@RestController
@RequestMapping("/product")
@Validated
public class ProductController {
	
	@PostMapping
	public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO productDTO){
		return null;
	}

	@GetMapping
	public ResponseEntity<?> list(){
		return null;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable("id") UUID id){
		return null;
	}
	
	@PutMapping
	public ResponseEntity<?> update(){
		return null;
	}
	
	public void delete(UUID id) {
		
	}
	
}
