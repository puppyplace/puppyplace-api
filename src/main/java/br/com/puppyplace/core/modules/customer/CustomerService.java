package br.com.puppyplace.core.modules.customer;

import br.com.puppyplace.core.entities.Customer;
import br.com.puppyplace.core.modules.customer.dto.CustomerDTO;

import java.util.UUID;

public interface CustomerService {
    public CustomerDTO create(CustomerDTO customerDTO);
    public CustomerDTO update(CustomerDTO customerDTO, UUID id);
    public CustomerDTO get(UUID id);
    public void delete(UUID id);
    public Customer findOne(UUID id);
}
