package br.com.puppyplace.core.modules.address.impl;

import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;
import br.com.puppyplace.core.entities.Address;
import br.com.puppyplace.core.entities.Customer;
import br.com.puppyplace.core.modules.address.AddressRepository;
import br.com.puppyplace.core.modules.customer.CustomerRepository;
import br.com.puppyplace.core.modules.customer.dto.AddressDTO;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AddressServiceImplTest {
    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CustomerRepository customerRepository;

    private EasyRandom easyRandom;
    private AddressDTO addressDTO;
    private Address address;
    private UUID addressID;

    private Customer customer;
    private UUID customerID;

    @BeforeEach
    void init(){
        this.easyRandom = new EasyRandom();
        this.addressDTO = easyRandom.nextObject(AddressDTO.class);

        this.address = easyRandom.nextObject(Address.class);
        this.addressID = UUID.randomUUID();

        this.customer = easyRandom.nextObject(Customer.class);
        this.customerID = UUID.randomUUID();

        ReflectionTestUtils.setField(addressService, "mapper", new ModelMapper());
    }

    @Test
    void shouldReturnSuccess_whenCreateANewAddress(){
        // given
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(customerRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.ofNullable(customer));

        // when
        var addressDTOPersisted = addressService.create(customerID, addressDTO);

        // then
        assertNotNull(addressDTOPersisted);
        assertEquals(addressDTO.getId(), addressDTOPersisted.getId());
        assertEquals(addressDTO.getStreet(), addressDTOPersisted.getStreet());
        assertEquals(addressDTO.getNumber(), addressDTOPersisted.getNumber());
        assertEquals(addressDTO.getComplement(), addressDTOPersisted.getComplement());
        assertEquals(addressDTO.getZipcode(), addressDTOPersisted.getZipcode());
        assertEquals(addressDTO.getState(), addressDTOPersisted.getState());

        verify(customerRepository, times(1)).findById(any(UUID.class));
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void shouldReturnError_whenCreateANewAddress(){
        // given
        when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // when
        assertThrows(ResourceNotFoundException.class, () -> {
            addressService.create(customerID, addressDTO);
        });

        // then
        verify(customerRepository, times(1)).findById(any(UUID.class));
        verify(addressRepository, times(0)).save(any(Address.class));
    }
}
