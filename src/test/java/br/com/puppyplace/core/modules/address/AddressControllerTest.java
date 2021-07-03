package br.com.puppyplace.core.modules.address;

import br.com.puppyplace.core.commons.enums.StateEnum;
import br.com.puppyplace.core.modules.address.dto.AddressDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private UUID addressID;
    private UUID customerID;

    @BeforeEach
    void init(){
        this.easyRandom = new EasyRandom();
        this.addressDTO = easyRandom.nextObject(AddressDTO.class);
        this.invalidAddressDTO = AddressDTO.builder().city("").street("")
                                    .number("").complement("").zipcode("").state(StateEnum.AC).build();
        this.customerID = UUID.randomUUID();
        this.addressID = UUID.randomUUID();
    }

    @Test
    @Disabled
    void shouldReturnSuccess_whenSendAValidAddressToCreate() throws Exception {
        // given
        when(addressService.create(customerID, addressDTO)).thenReturn(addressDTO);

        // when
        httpRequest.perform(post("/customer/{customer_id}/address",customerID.toString())
            .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(addressDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").value(addressDTO.getId().toString()))
            .andExpect(jsonPath("street").value(addressDTO.getStreet()))
            .andExpect(jsonPath("number").value(addressDTO.getNumber()))
            .andExpect(jsonPath("complement").value(addressDTO.getComplement()))
            .andExpect(jsonPath("zipcode").value(addressDTO.getZipcode()))
            .andExpect(jsonPath("city").value(addressDTO.getCity()))
            .andExpect(jsonPath("state").value(addressDTO.getState().toString()))
            .andExpect(jsonPath("principal").value(addressDTO.isMain()));

        // then
        verify(addressService, times(1)).create(customerID, addressDTO);
    }

    @Test
    void shouldReturnError_whenSendAInvalidAddressToCreate() throws Exception {
        // when
        httpRequest.perform(post("/customer/{customer_id}/address", customerID.toString())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(invalidAddressDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("messages").isNotEmpty())
                .andExpect(jsonPath("messages").isArray());

        // then
        verify(addressService, times(0)).create(customerID, addressDTO);
    }

    @Test
    void shouldReturnError_whenSendAInvalidCustomerToCreateAddress() throws Exception {
        // when
        httpRequest.perform(post("/customer/{customer_id}/address", "12345")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(addressDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("message").isNotEmpty());

        // then
        verify(addressService, times(0)).create(any(UUID.class), any(AddressDTO.class));
    }

    @Test
    @Disabled
    void shouldReturnSuccess_whenUpdateAddressWithValidPayload() throws Exception {
        // given
        when(addressService.update(customerID, addressID, addressDTO)).thenReturn(addressDTO);

        // when
        httpRequest.perform(put("/customer/{customer_id}/address/{id}", customerID.toString(), addressID.toString())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(addressDTO)))
                .andExpect(status().isOk()).andExpect(jsonPath("id").value(addressDTO.getId().toString()))
                .andExpect(jsonPath("street").value(addressDTO.getStreet()))
                .andExpect(jsonPath("number").value(addressDTO.getNumber()))
                .andExpect(jsonPath("complement").value(addressDTO.getComplement()))
                .andExpect(jsonPath("zipcode").value(addressDTO.getZipcode()))
                .andExpect(jsonPath("city").value(addressDTO.getCity()))
                .andExpect(jsonPath("state").value(addressDTO.getState().toString()))
                .andExpect(jsonPath("principal").value(addressDTO.isMain()));

        // then
        verify(addressService, times(1)).update(customerID, addressID, addressDTO);
    }

    @Test
    void shouldReturnError_whenUpdateAddressWithInvalidPayload() throws Exception {
        // when
        httpRequest.perform(put("/customer/{customer_id}/address/{id}", customerID.toString(), addressID.toString())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(invalidAddressDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("messages").isNotEmpty())
                .andExpect(jsonPath("messages").isArray());
        // then
        verify(addressService, times(0)).update(customerID, addressID, addressDTO);
    }

    @Test
    @Disabled
    void shouldReturnSuccess_whenDeleteAddress() throws Exception {
        // when
        httpRequest.perform(delete("/customer/{customer_id}/address/{id}", customerID.toString(), addressID.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // then
        verify(addressService, times(1)).delete(customerID, addressID);
    }

    @Test
    void shouldReturnError_whenDeleteAddressWithInvalidID() throws Exception {
        // when
        httpRequest.perform(delete("/customer/{customer_id}/address/{id}", customerID.toString(), "123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("message").isNotEmpty());

        // verify
        verify(addressService, times(0)).delete(customerID, addressID);
    }

    @Test
    @Disabled
    void shouldReturnSuccess_WhenGetAddressWithValidID() throws Exception{
        // given
        when(addressService.get(customerID, addressID)).thenReturn(addressDTO);

        // when
        httpRequest.perform(get("/customer/{customer_id}/address/{id}", customerID.toString(), addressID.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(addressDTO.getId().toString()))
                .andExpect(jsonPath("street").value(addressDTO.getStreet()))
                .andExpect(jsonPath("number").value(addressDTO.getNumber()))
                .andExpect(jsonPath("complement").value(addressDTO.getComplement()))
                .andExpect(jsonPath("zipcode").value(addressDTO.getZipcode()))
                .andExpect(jsonPath("city").value(addressDTO.getCity()))
                .andExpect(jsonPath("state").value(addressDTO.getState().toString()))
                .andExpect(jsonPath("principal").value(addressDTO.isMain()));

        // then
        verify(addressService, times(1)).get(customerID, addressID);
    }

    @Test
    void shouldReturnError_WhenGetAddressWithInvalidID() throws Exception{
        // when
        httpRequest.perform(get("/customer/{customer_id}/address/{id}", customerID.toString(), "10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("message").isNotEmpty());

        // then
        verify(addressService, times(0)).get(customerID, addressID);
    }

    @Test
    void shouldReturnSuccess_whenGetListOfAddresses() throws Exception {
        // given
        List<AddressDTO> addressList = easyRandom.objects(AddressDTO.class, 5).collect(Collectors.toList());
        var page = new PageImpl<AddressDTO>(addressList);
        when(addressService.list(any(PageRequest.class), any(UUID.class))).thenReturn(page);

        // when
        httpRequest.perform(get("/customer/{customer_id}/address/", customerID.toString())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("content").isArray()).andExpect(jsonPath("pageable").isNotEmpty());

        // then
        verify(addressService, times(1)).list(any(PageRequest.class), any(UUID.class));
    }

    @ParameterizedTest
    @CsvSource({ "size, -1", "page, -2" })
    void shouldReturnError_whenGetListOfAddressesWithInvalidSizeOrPage(String parameter, String value) throws Exception {
        // when
        httpRequest.perform(get("/customer/{customer_id}/address/", customerID.toString())
                .contentType(MediaType.APPLICATION_JSON).param(parameter, value))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("messages").isNotEmpty()).andExpect(jsonPath("messages").isArray());

        // then
        verify(addressService, times(0)).list(any(PageRequest.class), any(UUID.class));
    }
}
