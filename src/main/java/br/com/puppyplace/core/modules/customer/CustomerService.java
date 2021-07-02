package br.com.puppyplace.core.modules.customer;

import br.com.puppyplace.core.entities.Customer;
import br.com.puppyplace.core.modules.customer.dto.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CustomerService {
    CustomerDTO create(CustomerDTO customerDTO);
    CustomerDTO update(CustomerDTO customerDTO, UUID id);
    CustomerDTO get(UUID id);
    void delete(UUID id);
    Customer findOne(UUID id);
    Page<CustomerDTO> list(Pageable pageable);
    CustomerDTO findByEmail(String email);
}
