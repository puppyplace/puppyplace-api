package br.com.puppyplace.core.modules.customer.impl;

import br.com.puppyplace.core.commons.exceptions.BusinessException;
import br.com.puppyplace.core.commons.exceptions.ResourceAlreadyInUseException;
import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;
import br.com.puppyplace.core.entities.Customer;
import br.com.puppyplace.core.modules.customer.CustomerRepository;
import br.com.puppyplace.core.modules.customer.CustomerService;
import br.com.puppyplace.core.modules.customer.dto.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public CustomerDTO create(CustomerDTO customerDTO) {
        var referenceDate = LocalDate.now();
//        var birthday = customerDTO.getBirthdate();
//        var period = Period.between(birthday, referenceDate).getYears();
//
//        if (period < 18) {
//            throw new BusinessException("Customer must be greather than 18 years old");
//        }

        var existingCustomer = customerRepository.findByDocument(customerDTO.getDocument());
        if (existingCustomer.isPresent()) {
            log.error(">>> Customer is already in use. Returning error to client");
            throw new ResourceAlreadyInUseException("Customer is already in use");
        }

        var customer = mapper.map(customerDTO, Customer.class);
        customer.setCreatedAt(new Date());
        customer.setUpdatedAt(new Date());

        customerRepository.save(customer);
        customerDTO.setId(customer.getId());
        log.info(">>> Entity persisted!");
        return customerDTO;
    }

    public CustomerDTO update(CustomerDTO customerDTO, UUID id){
        try {
            log.info(">>> Starting update entity");

            Customer storageCustomer = this.findOne(id);
            var customer = mapper.map(customerDTO, Customer.class);
            customer.setId(id);
            customer.setUpdatedAt(new Date());
            customer.setCreatedAt(storageCustomer.getCreatedAt());

            customerRepository.save(customer);

            log.info(">>> Entity persisted!");
            customerDTO.setId(id);
            return customerDTO;
        } catch (DataIntegrityViolationException e) {
            log.error(">>> An exception occurred! {}", e.getMessage());
            throw new BusinessException("A business exception ocurred. Please verify the values of request body.");
        }
    }

    public CustomerDTO get(UUID customerID){
        var customer = this.findOne(customerID);
        log.info(">>> Building customer DTO from address entity");
        var customerDTO = mapper.map(customer, CustomerDTO.class);
        log.info(">>> Done");

        return customerDTO;
    }
    public void delete(UUID id) {
        log.info(">>> Get customer to delete.");

        // TODO: if customer has orders, the customer can not be deleted;
        var customer = this.findOne(id);
//        customer.setActive(Boolean.FALSE);
//        customer.setUpdatedAt(new Date());
//        customerRepository.save(customer);
        customerRepository.delete(customer);
    }

    @Override
    public Customer findOne(UUID id){
        log.info(">>> Starting find customer with ID {}", id);

        return customerRepository.findById(id).orElseThrow(() -> {
            log.error(">>> Customer not found with ID {}", id);
            throw new ResourceNotFoundException("No customer found with ID " + id.toString());
        });
    }

    @Override
    public CustomerDTO findByEmail(String email) {
        log.info(">>> Starting find customer with email {}", email);

         var customer = customerRepository.findByEmail(email).orElseThrow(() -> {
            log.error(">>> Customer not found with email {}", email);
            throw new ResourceNotFoundException("No customer found with email " + email);
        });

        var customerDTO = mapper.map(customer, CustomerDTO.class);

        return customerDTO;
    }

    public Page<CustomerDTO> list(Pageable pageable) {
        log.info(">>> Searching categories list from database");

        var pageOfCustomers = customerRepository.findAll(pageable);
        var pageOfCustomersDTO = pageOfCustomers.map(
                customer -> mapper.map(customer, CustomerDTO.class)
        );

        log.info(">>> Done");
        return pageOfCustomersDTO;
    }
}
