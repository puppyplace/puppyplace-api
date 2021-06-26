package br.com.puppyplace.core.modules.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.puppyplace.core.modules.product.dto.ProductDTO;
import br.com.puppyplace.core.modules.product.service.ProductService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

	@Autowired
	private MockMvc httpRequest;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private ProductService productService;

	private EasyRandom easyRandom;

	private ProductDTO productDTO;

	private ProductDTO invalidProductDTO;

	@BeforeEach
	void init(){
		this.easyRandom = new EasyRandom();
		this.productDTO = easyRandom.nextObject(ProductDTO.class);
		this.invalidProductDTO = ProductDTO.builder().title("").description("").idCategories(null).build();
	}

	@Test
	void shouldReturnSuccess_whenSendAValidProductToCreate() throws Exception {
		// given
		when(productService.create(any(ProductDTO.class))).thenReturn(productDTO);

		// when
		httpRequest
				.perform(
						post("/product").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(productDTO)))
				.andExpect(status().isCreated()).andExpect(jsonPath("id").value(productDTO.getId().toString()))
				.andExpect(jsonPath("description").value(productDTO.getDescription()))
				.andExpect(jsonPath("avatar_url").value(productDTO.getAvatarUrl().toString()))
				.andExpect(jsonPath("product_code").value(productDTO.getProductCode().toString()))
				.andExpect(jsonPath("title").value(productDTO.getTitle()));

		// then
		verify(productService, times(1)).create(productDTO);
	}

	@Test
	void shouldReturnError_whenSendAInvalidProductToCreate() throws Exception {
		// when
		httpRequest
				.perform(post("/product").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(invalidProductDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
				.andExpect(jsonPath("messages").isNotEmpty()).andExpect(jsonPath("messages").isArray());

		// then
		verify(productService, times(0)).create(invalidProductDTO);
	}

	@Test
	void shouldReturnSuccess_whenGetListOfProducts() throws Exception {
		// given
		List<ProductDTO> productList = easyRandom.objects(ProductDTO.class, 10).collect(Collectors.toList());
		var page = new PageImpl<ProductDTO>(productList);
		when(productService.list(any(PageRequest.class))).thenReturn(page);

		// when
		httpRequest.perform(get("/product").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("content").isArray()).andExpect(jsonPath("pageable").isNotEmpty());

		// then
		verify(productService, times(1)).list(any(PageRequest.class));
	}

	@ParameterizedTest
	@CsvSource({ "size, -1", "page, -2" })
	void shouldReturnError_whenGetListOfProductsWithInvalidSizeOrPage(String parameter, String value) throws Exception {
		// when
		httpRequest.perform(get("/product").accept(MediaType.APPLICATION_JSON).param(parameter, value))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
				.andExpect(jsonPath("messages").isNotEmpty()).andExpect(jsonPath("messages").isArray());

		// then
		verify(productService, times(0)).list(any(PageRequest.class));
	}

	@Test
	void shouldReturnSuccess_WhenGetUserWithValidID() throws Exception{
		// given	
		when(productService.get(any(UUID.class))).thenReturn(productDTO);

		// when
		httpRequest.perform(get("/product/{id}", UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("description").value(productDTO.getDescription()))
			.andExpect(jsonPath("avatar_url").value(productDTO.getAvatarUrl().toString()))
			.andExpect(jsonPath("product_code").value(productDTO.getProductCode().toString()))
			.andExpect(jsonPath("title").value(productDTO.getTitle()));

			// then
		verify(productService, times(1)).get(any(UUID.class));
	}

	@Test
	void shouldReturnError_WhenGetUserWithoutID() throws Exception{
		// when
		httpRequest.perform(get("/product/{id}", "10").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
			.andExpect(jsonPath("message").isNotEmpty());

		// then
		verify(productService, times(0)).get(any(UUID.class));
	}


	@Test
	void shouldReturnSuccess_whenUpdateProductWithValidPayload() throws Exception {
		// given
		when(productService.update(any(ProductDTO.class), any(UUID.class))).thenReturn(productDTO);

		// when
		httpRequest
				.perform(put("/product/{id}", UUID.randomUUID().toString())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(productDTO)))
				.andExpect(status().isOk()).andExpect(jsonPath("id").value(productDTO.getId().toString()))
				.andExpect(jsonPath("description").value(productDTO.getDescription()))
				.andExpect(jsonPath("avatar_url").value(productDTO.getAvatarUrl().toString()))
				.andExpect(jsonPath("product_code").value(productDTO.getProductCode().toString()))
				.andExpect(jsonPath("title").value(productDTO.getTitle()));

		// then
		verify(productService, times(1)).update(any(ProductDTO.class), any(UUID.class));
	}

	@Test
	void shouldReturnError_whenUpdateProductWithInvalidPayload() throws Exception {
		// when
		httpRequest
				.perform(put("/product/{id}", UUID.randomUUID().toString()).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(invalidProductDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
				.andExpect(jsonPath("messages").isNotEmpty()).andExpect(jsonPath("messages").isArray());

		// then
		verify(productService, times(0)).update(any(ProductDTO.class), any(UUID.class));
	}

	@Test
	void shouldReturnError_whenUpdateProductWithInvalidID() throws Exception {
		// when
		httpRequest
			.perform(put("/product/{id}", "10").contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(productDTO)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
			.andExpect(jsonPath("message").isNotEmpty());

		// then
		verify(productService, times(0)).update(any(ProductDTO.class), any(UUID.class));
	}

	@Test
	void shouldReturnSuccess_whenDeleteProduct() throws Exception {
		// when
		httpRequest
			.perform(delete("/product/{id}", UUID.randomUUID().toString())
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());	

		// then
		verify(productService, times(1)).delete(any(UUID.class));
	}

	@Test
	void shouldReturnError_whenDeleteProductWithInvalidID() throws Exception {
		// when
		httpRequest
			.perform(delete("/product/{id}", "10")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
			.andExpect(jsonPath("message").isNotEmpty());

		// then
		verify(productService, times(0)).delete(any(UUID.class));
	}
}
