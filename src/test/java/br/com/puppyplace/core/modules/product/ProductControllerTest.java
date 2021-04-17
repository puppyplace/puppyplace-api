package br.com.puppyplace.core.modules.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.puppyplace.core.modules.product.dto.ProductDTO;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {
	
	@Autowired
	private MockMvc httpRequest;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private ProductService productService;	

	private EasyRandom easyRandom = new EasyRandom();

	@Test
	void shoudCreateProduct_whenSendAValidProduct() throws Exception {
		//given
		var productDTO = easyRandom.nextObject(ProductDTO.class);
		when(productService.create(any(ProductDTO.class))).thenReturn(productDTO);
		
		//when
		httpRequest.perform(post("/product").contentType("application/json").content(mapper.writeValueAsString(productDTO)))
			.andExpect(status().isCreated()).andExpect(jsonPath("id").value(productDTO.getId().toString()))
			.andExpect(jsonPath("description").value(productDTO.getDescription()))
			.andExpect(jsonPath("title").value(productDTO.getTitle()));
		
		//then
		verify(productService, times(1)).create(productDTO);
	}

}
