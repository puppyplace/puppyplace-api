package br.com.puppyplace.core.modules.customer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.puppyplace.core.entities.Customer;
import br.com.puppyplace.core.modules.customer.dto.CustomerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {
    @Autowired
    private MockMvc httpRequest;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CustomerService customerService;

    private EasyRandom easyRandom;
    private CustomerDTO customerDTO;
    private CustomerDTO invalidCustomerDTO;

    @BeforeEach
    void init(){
        this.easyRandom = new EasyRandom();
        this.customerDTO = easyRandom.nextObject(CustomerDTO.class);
        this.customerDTO.setBirthDate(LocalDate.of(2000, 12, 01));
        this.invalidCustomerDTO = CustomerDTO.builder()
                                    .name("")
                                    .email("")
                                    .document("")
                                    .cellphone("")
                                    .birthDate(LocalDate.now())
                                    .password("")
                                    .build();
    }

    @Test
    void shouldReturnSuccess_whenSendAValidCustomerToCreate() throws Exception {
        // given
        when(customerService.create(any(CustomerDTO.class))).thenReturn(customerDTO);

        // when
        httpRequest.perform(post("/product").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(customerDTO)))
                .andExpect(status().isCreated()).andExpect(jsonPath("id").value(customerDTO.getId().toString()))
                .andExpect(jsonPath("name").value(customerDTO.getName()))
                .andExpect(jsonPath("document").value(customerDTO.getDocument()))
                .andExpect(jsonPath("email").value(customerDTO.getEmail()))
                .andExpect(jsonPath("cellphone").value(customerDTO.getCellphone()))
                .andExpect(jsonPath("birthdate").value(customerDTO.getBirthDate()));
        // then
        verify(customerService, times(1)).create(customerDTO);
    }

    @Test
    void shouldReturnError_whenSendAInvalidCustomerToCreate() throws Exception {
        customerDTO.setBirthDate(LocalDate.of(2050, 12, 01));
        // when
        httpRequest.perform(post("/customer").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(customerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("messages").isNotEmpty()).andExpect(jsonPath("messages").isArray());
        // then
        verify(customerService, times(0)).create(customerDTO);
    }

    @Test
    void shouldReturnError_whenSendACustomerWithAFutureBirthdDate() throws Exception {
        invalidCustomerDTO.setBirthDate(LocalDate.of(2050, 12, 01));
        // when
        httpRequest.perform(post("/customer").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(invalidCustomerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("messages").isNotEmpty()).andExpect(jsonPath("messages").isArray());
        // then
        verify(customerService, times(0)).create(invalidCustomerDTO);
    }

    @Test
    void shouldReturnSuccess_whenUpdateCustomerWithValidPayload() throws Exception {
        // given
        when(customerService.update(any(CustomerDTO.class),any(UUID.class))).thenReturn(customerDTO);

        // when
        httpRequest.perform(put("/customer/{id}", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(customerDTO)))
                .andExpect(status().isOk()).andExpect(jsonPath("id").value(customerDTO.getId().toString()))
                .andExpect(jsonPath("name").value(customerDTO.getName()))
                .andExpect(jsonPath("document").value(customerDTO.getDocument()))
                .andExpect(jsonPath("email").value(customerDTO.getEmail()))
                .andExpect(jsonPath("cellphone").value(customerDTO.getCellphone()))
                .andExpect(jsonPath("birthdate").value(customerDTO.getBirthDate().toString()));
        // then
        verify(customerService, times(1)).update(any(CustomerDTO.class), any(UUID.class));
    }

    @Test
    void shouldReturnError_whenUpdateCustomerWithInvalidPayload() throws Exception {
        // when
        httpRequest.perform(put("/customer/{id}", UUID.randomUUID().toString()).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(invalidCustomerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("messages").isNotEmpty()).andExpect(jsonPath("messages").isArray());

        // then
        verify(customerService, times(0)).update(any(CustomerDTO.class), any(UUID.class));
    }

    @Test
    void shouldReturnSuccess_whenDeleteCustomer() throws Exception {
        // when
        httpRequest.perform(delete("/customer/{id}", UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // then
        verify(customerService, times(1)).delete(any(UUID.class));
    }

    @Test
    void shouldReturnError_whenDeleteCustomerWithInvalidID() throws Exception {
        // when
        httpRequest.perform(delete("/customer/{id}", "123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("message").isNotEmpty());

        // verify
        verify(customerService, times(0)).delete(any(UUID.class));

    }
}
