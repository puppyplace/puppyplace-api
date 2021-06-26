package br.com.puppyplace.core.modules.customer;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private UUID customerID;

    @BeforeEach
    void init(){
        this.easyRandom = new EasyRandom();
        this.customerDTO = easyRandom.nextObject(CustomerDTO.class);

        var validEmail = "fulano@email.com";
        this.customerDTO.setEmail(validEmail);

        var validCPF = "46797310016";
        this.customerDTO.setDocument(validCPF);
        this.customerDTO.setBirthdate(LocalDate.of(2000, 12, 01));
        this.customerID = UUID.randomUUID();
        this.invalidCustomerDTO = CustomerDTO.builder()
                                    .name("")
                                    .email("")
                                    .document("")
                                    .cellphone("")
                                    .birthdate(LocalDate.now())
                                    .password("")
                                    .build();
    }

    @Test
    void shouldReturnSuccess_whenSendAValidCustomerToCreate() throws Exception {
        // given
        when(customerService.create(any(CustomerDTO.class))).thenReturn(customerDTO);

        // when
        httpRequest.perform(post("/customer").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(customerDTO)))
                .andExpect(status().isCreated());

        // then
        verify(customerService, times(1)).create(customerDTO);
    }

    @Test
    void shouldReturnError_whenSendAInvalidCustomerToCreate() throws Exception {
        customerDTO.setBirthdate(LocalDate.of(2050, 12, 01));
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
    void shouldReturnError_whenSendAValidCustomerWithInvalidDocumentToCreate() throws Exception {
        var invalidCPF="46797310018";
        customerDTO.setDocument(invalidCPF);
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
    void shouldReturnError_whenSendACustomerWithAFutureBirthDate() throws Exception {
        customerDTO.setBirthdate(LocalDate.of(2050, 12, 01));
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
                .andExpect(jsonPath("birthdate").value(customerDTO.getBirthdate().toString()));
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

    @Test
    void shouldReturnSuccess_whenGetCustomerWithValidID() throws Exception {
        // given
        when(customerService.get(customerID)).thenReturn(customerDTO);

        // when
        httpRequest.perform(get("/customer/{id}", customerID.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(customerDTO.getId().toString()))
                .andExpect(jsonPath("name").value(customerDTO.getName()))
                .andExpect(jsonPath("document").value(customerDTO.getDocument()))
                .andExpect(jsonPath("email").value(customerDTO.getEmail()))
                .andExpect(jsonPath("cellphone").value(customerDTO.getCellphone()))
                .andExpect(jsonPath("birthdate").value(customerDTO.getBirthdate().toString()))
                .andExpect(jsonPath("password").value(customerDTO.getPassword()));

        // then
        verify(customerService, times(1)).get(customerID);


    }

    @Test
    void shouldReturnError_whenGetCustomerWithInvalidID() throws Exception {
        // when
        httpRequest.perform(get("/customer/{id}/", "10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("message").isNotEmpty());

        // then
        verify(customerService, times(0)).get(customerID);
    }
}
