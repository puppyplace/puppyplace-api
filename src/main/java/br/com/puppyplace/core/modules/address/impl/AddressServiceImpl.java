package br.com.puppyplace.core.modules.address.impl;

import br.com.puppyplace.core.commons.exceptions.BusinessException;
import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;
import br.com.puppyplace.core.entities.Address;
import br.com.puppyplace.core.modules.address.AddressRepository;
import br.com.puppyplace.core.modules.address.AddressService;
import br.com.puppyplace.core.modules.customer.CustomerService;
import br.com.puppyplace.core.modules.customer.dto.AddressDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    private CustomerService customerService;

    @Override
    public AddressDTO create(UUID customerID, AddressDTO addressDTO) {
        var customer = customerService.findOne(customerID);

        var address = mapper.map(addressDTO, Address.class);
        address.setCustomer(customer);
        address.setCreatedAt(new Date());
        address.setUpdatedAt(new Date());

        addressRepository.save(address);
        addressDTO.setId(address.getId());
        log.info(">>> Entity persisted!");
        return addressDTO;
    }

    public AddressDTO update(UUID customerID, UUID addressID, AddressDTO addressDTO){
        try{
            log.info(">>> Starting update entity");
            this.findOne(addressID);
            var address = mapper.map(addressDTO, Address.class);
            address.setId(addressID);
            address.setUpdatedAt(new Date());

            addressRepository.save(address);
            log.info(">>> Entity persisted!");
            addressDTO.setId(addressID);

            return addressDTO;
        } catch (DataIntegrityViolationException e){
            log.error(">>> An exception occurred! {}", e.getMessage());
            throw new BusinessException("A business exception occurred. Please verify the values of request body.");
        }
    }

    public void delete(UUID customerID, UUID addressID) {
        log.info(">>> Get address to delete.");
        var address = this.findOne(addressID);
        addressRepository.delete(address);
    }

    private Address findOne(UUID addressID){
        log.info(">>> Starting find address with ID {}", addressID);

        return addressRepository.findById(addressID).orElseThrow(() -> {
            log.error(">>> Address not found with ID {}", addressID);
            throw new ResourceNotFoundException("No address found with ID " + addressID);
        });
    }
}
