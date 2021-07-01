package br.com.puppyplace.core.modules.customer.impl;

import br.com.puppyplace.core.commons.exceptions.BusinessException;
import br.com.puppyplace.core.commons.exceptions.ResourceAlreadyInUseException;
import br.com.puppyplace.core.entities.Customer;
import br.com.puppyplace.core.modules.customer.CustomerRepository;
import br.com.puppyplace.core.modules.customer.dto.CustomerDTO;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CustomerServiceImplTest {
    @InjectMocks
    private  CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private EasyRandom easyRandom;
    private CustomerDTO customerDTO;
    private Customer customer;
    private UUID customerID;

    @BeforeEach
    void init(){
        this.easyRandom = new EasyRandom();
        this.customerDTO = easyRandom.nextObject(CustomerDTO.class);
        this.customerDTO.setBirthdate(LocalDate.of(2000, 12, 01));

        this.customer = easyRandom.nextObject(Customer.class);
        this.customer.setBirthdate(LocalDate.of(2000, 12, 01));
        this.customerID = UUID.randomUUID();

        ReflectionTestUtils.setField(customerService, "mapper", new ModelMapper());
    }

    @Test
    void shouldReturnSuccess_whenCreateANewCustomer(){
        // given
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        // when
        var customerDTOPersisted = customerService.create(customerDTO);

        // then
        assertNotNull(customerDTOPersisted);
        assertEquals(customerDTO.getId(), customerDTOPersisted.getId());
        assertEquals(customerDTO.getFirstName(), customerDTOPersisted.getFirstName());
        assertEquals(customerDTO.getEmail(), customerDTOPersisted.getEmail());
        assertEquals(customerDTO.getDocument(), customerDTOPersisted.getDocument());
        assertEquals(customerDTO.getCellphone(), customerDTOPersisted.getCellphone());
        assertEquals(customerDTO.getBirthdate(), customerDTOPersisted.getBirthdate());

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void shouldReturnError_whenCreateANewCustomerWithLessEighteenYearsOld(){
        // given
        customerDTO.setBirthdate(LocalDate.now().minusYears(17));
        // then
        assertThrows(BusinessException.class, () -> {
            customerService.create(customerDTO);
        });

        verify(customerRepository, times(0)).save(any(Customer.class));
    }

    @Test
    void shouldReturnError_whenCreateAnExistentCustomer(){
        // given
        when(customerRepository.findByDocument(any(String.class))).thenReturn(Optional.of(customer));
        // then
        assertThrows(ResourceAlreadyInUseException.class, () -> {
            customerService.create(customerDTO);
        });

        verify(customerRepository, times(0)).save(any(Customer.class));
    }

    @Test
    void shouldReturnSuccess_whenUpdateCustomer(){
        // given
        when(customerRepository.findById(customerID)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // when
        var customerDTOPersisted = customerService.update(customerDTO, customerID);

        // then
        assertNotNull(customerDTOPersisted);
        assertEquals(customerDTO.getId(), customerDTOPersisted.getId());
        assertEquals(customerDTO.getFirstName(), customerDTOPersisted.getFirstName());
        assertEquals(customerDTO.getEmail(), customerDTOPersisted.getEmail());
        assertEquals(customerDTO.getDocument(), customerDTOPersisted.getDocument());
        assertEquals(customerDTO.getCellphone(), customerDTOPersisted.getCellphone());
        assertEquals(customerDTO.getBirthdate(), customerDTOPersisted.getBirthdate());

        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(customerRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void shouldReturnError_whenUpdateCustomerThatViolatesDataIntegrity(){
        //given
        when(customerRepository.findById(customerID)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenThrow(new DataIntegrityViolationException(""));

        //then
        assertThrows(BusinessException.class, () -> {
            customerService.update(customerDTO, customerID);
        });

        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(customerRepository, times(1)).findById(any(UUID.class));

    }

    @Test
    void shouldReturnSuccess_whenDeleteCustomer(){
        //given
        customer.setActive(Boolean.TRUE);
        when(customerRepository.findById(customerID)).thenReturn(Optional.of(customer));

        //when
        customerService.delete(customerID);

        //then
        verify(customerRepository, times(1)).delete(any(Customer.class));
        var argument = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).delete(argument.capture());
    }
}
