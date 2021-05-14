package br.com.puppyplace.core.modules.address.impl;

import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;
import br.com.puppyplace.core.entities.Address;
import br.com.puppyplace.core.modules.address.AddressRepository;
import br.com.puppyplace.core.modules.address.AddressService;
import br.com.puppyplace.core.modules.customer.CustomerRepository;
import br.com.puppyplace.core.modules.customer.dto.AddressDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public AddressDTO create(UUID customer_id, AddressDTO addressDTO) {
        var customer = customerRepository.findById(customer_id).orElseThrow(() -> {
            log.error(">>> Customer not found with ID {}", customer_id);
            throw new ResourceNotFoundException("No customer found with ID " + customer_id);
        });

        var address = mapper.map(addressDTO, Address.class);
        address.setCustomer(customer);
        address.setCreatedAt(new Date());
        address.setUpdatedAt(new Date());

        addressRepository.save(address);
        addressDTO.setId(address.getId());
        log.info(">>> Entity persisted!");
        return addressDTO;
    }
}
