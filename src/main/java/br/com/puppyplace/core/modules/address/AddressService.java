package br.com.puppyplace.core.modules.address;

import br.com.puppyplace.core.modules.customer.dto.AddressDTO;

import java.util.UUID;

public interface AddressService {
    public AddressDTO create(UUID customerID, AddressDTO addressDTO);
    public AddressDTO update(UUID customerID, UUID id, AddressDTO addressDTO);
}
