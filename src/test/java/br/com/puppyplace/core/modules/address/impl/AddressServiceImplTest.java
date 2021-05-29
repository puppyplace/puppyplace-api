package br.com.puppyplace.core.modules.address.impl;

import br.com.puppyplace.core.commons.exceptions.BusinessException;
import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;
import br.com.puppyplace.core.entities.Address;
import br.com.puppyplace.core.entities.Customer;
import br.com.puppyplace.core.modules.address.AddressRepository;
import br.com.puppyplace.core.modules.customer.CustomerService;
import br.com.puppyplace.core.modules.customer.dto.AddressDTO;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private CustomerService customerService;

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
        this.customerID = this.customer.getId();

        ReflectionTestUtils.setField(addressService, "mapper", new ModelMapper());
    }

    @Test
    void shouldReturnSuccess_whenCreateANewAddress(){
        // given
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(customerService.findOne(any(UUID.class))).thenReturn(customer);

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

        verify(customerService, times(1)).findOne(any(UUID.class));
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void shouldReturnError_whenCreateANewAddress(){
        // given
        when(customerService.findOne(any(UUID.class))).thenThrow(new ResourceNotFoundException());

        // when
        assertThrows(ResourceNotFoundException.class, () -> {
            addressService.create(customerID, addressDTO);
        });

        // then
        verify(customerService, times(1)).findOne(any(UUID.class));
        verify(addressRepository, times(0)).save(any(Address.class));
    }

    @Test
    void shouldReturnSuccess_whenUpdateAddress(){
        // given
        when(addressRepository.findById(addressID)).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        when(customerService.findOne(customerID)).thenReturn(customer);

        // when
        var addressDTOPersisted = addressService.update(customerID, addressID, addressDTO);

        // then
        assertNotNull(addressDTOPersisted);
        assertEquals(addressDTO.getId(), addressDTOPersisted.getId());
        assertEquals(addressDTO.getStreet(), addressDTOPersisted.getStreet());
        assertEquals(addressDTO.getNumber(), addressDTOPersisted.getNumber());
        assertEquals(addressDTO.getComplement(), addressDTOPersisted.getComplement());
        assertEquals(addressDTO.getZipcode(), addressDTOPersisted.getZipcode());
        assertEquals(addressDTO.getState(), addressDTOPersisted.getState());

        verify(addressRepository, times(1)).findById(addressID);
        verify(addressRepository, times(1)).save(any(Address.class));
        verify(customerService, times(1)).findOne(customerID);
    }

    @Test
    void shouldReturnError_whenUpdateAddressThatViolatesDataIntegrity(){
        //given
        when(addressRepository.findById(addressID)).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Address.class))).thenThrow(new DataIntegrityViolationException(""));

        //then
        assertThrows(BusinessException.class, () -> {
            addressService.update(customerID, addressID, addressDTO);
        });

        verify(addressRepository, times(1)).findById(any(UUID.class));
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void shouldReturnSuccess_WhenGetExistentAddress(){
        //given
        when(addressRepository.findById(addressID)).thenReturn(Optional.of(address));

        //when
        var addressDTO = addressService.get(customerID, addressID);

        //then
        assertNotNull(addressDTO);
        assertEquals(addressDTO.getId(), address.getId());
        assertEquals(addressDTO.getStreet(), address.getStreet());
        assertEquals(addressDTO.getNumber(), address.getNumber());
        assertEquals(addressDTO.getComplement(), address.getComplement());
        assertEquals(addressDTO.getZipcode(), address.getZipcode());
        assertEquals(addressDTO.getState(), address.getState());

        verify(addressRepository, times(1)).findById(addressID);
    }

    @Test
    void shouldReturnError_WhenNotFindAddress(){
        //given
        when(addressRepository.findById(addressID)).thenReturn(Optional.ofNullable(null));

        //then
        assertThrows(ResourceNotFoundException.class, () -> {
            addressService.get(customerID, addressID);
        });
        verify(addressRepository, times(1)).findById(addressID);
    }

    @Test
    void shouldReturnSuccess_WhenGetPageOfAddresses(){
        //given
        var listOfProduct = easyRandom.objects(Address.class, 2)
                .collect(Collectors.toList());
        var pageOfProducts = new PageImpl<>(listOfProduct);
        when(addressRepository.findByCustomerId(any(UUID.class), any(Pageable.class))).thenReturn(pageOfProducts);

        //when
        var pageOfAddressDTO = addressService.list(PageRequest.of(1, 10), customerID);

        //then
        assertNotNull(pageOfAddressDTO);
        assertEquals(listOfProduct.size(), pageOfAddressDTO.getContent().size());
        verify(addressRepository, times(1)).findByCustomerId(any(UUID.class), any(Pageable.class));
    }

    @Test
    void shouldReturnSuccess_whenDeleteAddress(){
        //given
        when(addressRepository.findById(addressID)).thenReturn(Optional.of(address));

        //when
        addressService.delete(customerID, addressID);

        //then
        verify(addressRepository, times(1)).delete(address);
        var argument = ArgumentCaptor.forClass(Address.class);
        verify(addressRepository).delete(argument.capture());
    }
}
