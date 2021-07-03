package br.com.puppyplace.core.modules.address;

import br.com.puppyplace.core.modules.address.dto.AddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AddressService {
     AddressDTO create(UUID customerID, AddressDTO addressDTO);
     AddressDTO update(UUID customerID, UUID id, AddressDTO addressDTO);
     UUID delete(UUID customerID, UUID addressID);
     AddressDTO get(UUID customerID, UUID addressID);
     Page<AddressDTO> list(Pageable pageable, UUID customerID);
     UUID makeMain(UUID customerID, UUID addressID);
}
