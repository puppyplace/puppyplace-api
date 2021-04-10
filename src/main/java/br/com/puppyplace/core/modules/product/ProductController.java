package br.com.puppyplace.core.modules.product;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@PostMapping
	public ResponseEntity<?> create(){
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
