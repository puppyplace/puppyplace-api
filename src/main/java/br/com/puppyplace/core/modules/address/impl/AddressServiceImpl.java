package br.com.puppyplace.core.modules.address.impl;

import br.com.puppyplace.core.commons.exceptions.BusinessException;
import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;
import br.com.puppyplace.core.entities.Address;
import br.com.puppyplace.core.modules.address.AddressRepository;
import br.com.puppyplace.core.modules.address.AddressService;
import br.com.puppyplace.core.modules.customer.CustomerService;
import br.com.puppyplace.core.modules.address.dto.AddressDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            var foundAddress = this.findOne(addressID);
            var customer = customerService.findOne(customerID);
            var address = mapper.map(addressDTO, Address.class);
            address.setId(addressID);
            address.setUpdatedAt(new Date());
            address.setCreatedAt(foundAddress.getCreatedAt());
            address.setCustomer(customer);

            addressRepository.save(address);
            log.info(">>> Entity persisted!");
            addressDTO.setId(addressID);

            return addressDTO;
        } catch (DataIntegrityViolationException e){
            log.error(">>> An exception occurred! {}", e.getMessage());
            throw new BusinessException("A business exception occurred. Please verify the values of request body.");
        }
    }

    public UUID delete(UUID customerID, UUID addressID) {
        log.info(">>> Get address to delete.");
        var address = this.findOne(addressID);
        addressRepository.delete(address);

        return addressID;
    }

    public UUID makeMain(UUID customerID, UUID addressID) {
        log.info(">>> Get address to make main.");
        var addresses = addressRepository.findByCustomerId(customerID);
        addresses.stream().forEach(address -> {
            if ((address.getId().equals(addressID))) {
                address.setMain(true);
            } else {
                address.setMain(false);
            }
        });
        addressRepository.saveAll(addresses);

        return addressID;
    }

    public AddressDTO get(UUID customerID, UUID addressID){
        var address= this.findOne(addressID);

        log.info(">>> Building address DTO from address entity");
        var addressDTO = mapper.map(address, AddressDTO.class);
        log.info(">>> Done");

        return addressDTO;
    }

    public Page<AddressDTO> list(Pageable pageable, UUID customerID){
        log.info(">>> Searching addresses list from database");

        var pageOfAddresses = addressRepository.findByCustomerId(customerID, pageable);
        var pageOfProductsDTO = pageOfAddresses.map(address -> mapper.map(address, AddressDTO.class));

        log.info(">>> Done");
        return pageOfProductsDTO;
    }

    private Address findOne(UUID addressID) {
        log.info(">>> Starting find address with ID {}", addressID);

        return addressRepository.findById(addressID).orElseThrow(() -> {
            log.error(">>> Address not found with ID {}", addressID);
            throw new ResourceNotFoundException("No address found with ID " + addressID);
        });
    }
}
