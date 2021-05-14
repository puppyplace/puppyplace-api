package br.com.puppyplace.core.modules.address;

import br.com.puppyplace.core.modules.customer.dto.AddressDTO;

import java.util.UUID;

public interface AddressService {
    public AddressDTO create(UUID customer_id, AddressDTO customerDTO);
}
