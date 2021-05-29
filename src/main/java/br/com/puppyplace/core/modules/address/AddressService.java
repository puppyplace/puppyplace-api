package br.com.puppyplace.core.modules.address;

import br.com.puppyplace.core.modules.customer.dto.AddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AddressService {
    public AddressDTO create(UUID customerID, AddressDTO addressDTO);
    public AddressDTO update(UUID customerID, UUID id, AddressDTO addressDTO);
    public void delete(UUID customerID, UUID addressID);
    public AddressDTO get(UUID customerID, UUID addressID);
    public Page<AddressDTO> list(Pageable pageable, UUID customerID);
}
