package br.com.puppyplace.core.modules.address;

import br.com.puppyplace.core.commons.enums.StateEnum;
import br.com.puppyplace.core.modules.customer.dto.AddressDTO;
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

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AddressController.class)
public class AddressControllerTest {

    @Autowired
    private MockMvc httpRequest;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AddressService addressService;

    private EasyRandom easyRandom;
    private AddressDTO addressDTO;
    private AddressDTO invalidAddressDTO;

    @BeforeEach
    void init(){
        this.easyRandom = new EasyRandom();
        this.addressDTO = easyRandom.nextObject(AddressDTO.class);
        this.invalidAddressDTO = AddressDTO.builder().city("").street("")
                                    .number("").complement("").zipcode("").state(StateEnum.AC).build();
    }

    @Test
    void shouldReturnSuccess_whenSendAValidAddressToCreate() throws Exception {
        // given
        when(addressService.create(any(UUID.class), any(AddressDTO.class))).thenReturn(addressDTO);

        // when
        httpRequest.perform(post("/customer/{customer_id}/address", UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(addressDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").value(addressDTO.getId().toString()))
            .andExpect(jsonPath("street").value(addressDTO.getStreet()))
            .andExpect(jsonPath("number").value(addressDTO.getNumber()))
            .andExpect(jsonPath("complement").value(addressDTO.getComplement()))
            .andExpect(jsonPath("zipcode").value(addressDTO.getZipcode()))
            .andExpect(jsonPath("city").value(addressDTO.getCity()))
            .andExpect(jsonPath("state").value(addressDTO.getState().toString()));

        // then
        verify(addressService, times(1)).create(any(UUID.class), any(AddressDTO.class));
    }

    @Test
    void shouldReturnError_whenSendAInvalidAddressToCreate() throws Exception {
        // when
        httpRequest.perform(post("/customer/{customer_id}/address", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(invalidAddressDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("messages").isNotEmpty())
                .andExpect(jsonPath("messages").isArray());

        // then
        verify(addressService, times(0)).create(any(UUID.class), any(AddressDTO.class));
    }

    // create address with a invalid customer

    @Test
    void shouldReturnSuccess_whenUpdateAddressWithValidPayload() throws Exception {
        // given
        when(addressService.update(any(UUID.class), any(UUID.class), any(AddressDTO.class))).thenReturn(addressDTO);

        // when
        httpRequest.perform(put("/customer/{customer_id}/address/{id}", UUID.randomUUID().toString(), UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(addressDTO)))
                .andExpect(status().isOk()).andExpect(jsonPath("id").value(addressDTO.getId().toString()))
                .andExpect(jsonPath("street").value(addressDTO.getStreet()))
                .andExpect(jsonPath("number").value(addressDTO.getNumber()))
                .andExpect(jsonPath("complement").value(addressDTO.getComplement()))
                .andExpect(jsonPath("zipcode").value(addressDTO.getZipcode()))
                .andExpect(jsonPath("city").value(addressDTO.getCity()))
                .andExpect(jsonPath("state").value(addressDTO.getState().toString()));

        // then
        verify(addressService, times(1)).update(any(UUID.class), any(UUID.class), any(AddressDTO.class));
    }

    @Test
    void shouldReturnError_whenUpdateAddressWithInvalidPayload() throws Exception {
        // when
        httpRequest.perform(put("/customer/{customer_id}/address/{id}", UUID.randomUUID().toString(), UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(invalidAddressDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("messages").isNotEmpty())
                .andExpect(jsonPath("messages").isArray());
        // then
        verify(addressService, times(0)).update(any(UUID.class), any(UUID.class), any(AddressDTO.class));
    }

    @Test
    void shouldReturnSuccess_whenDeleteAddress() throws Exception {
        // when
        httpRequest.perform(delete("/customer/{customer_id}/address/{id}", UUID.randomUUID().toString(), UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // then
        verify(addressService, times(1)).delete(any(UUID.class), any(UUID.class));
    }

    @Test
    void shouldReturnError_whenDeleteAddressWithInvalidID() throws Exception {
        // when
        httpRequest.perform(delete("/customer/{customer_id}/address/{id}", UUID.randomUUID().toString(), "123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("message").isNotEmpty());

        // verify
        verify(addressService, times(0)).delete(any(UUID.class), any(UUID.class));

    }
}
